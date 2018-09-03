package com.dzytsiuk.onlinestore.security;

import com.dzytsiuk.onlinestore.entity.User;
import com.dzytsiuk.onlinestore.entity.security.AuthPrincipal;
import com.dzytsiuk.onlinestore.entity.security.Session;
import com.dzytsiuk.onlinestore.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class DefaultSecurityService implements SecurityService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final UserService userService;
    private List<Session> sessions = Collections.synchronizedList(new ArrayList<>());
    @Value("${session.ttl}")
    private long timeToLive;

    @Autowired
    public DefaultSecurityService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Optional<Session> auth(String login, String password) {
        logger.debug("Start  authentication for user {} ", login);
        Optional<User> optionalUser = userService.findByLogin(login);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            int hashedPass = Objects.hash(password, user.getSalt());
            int userPass = user.getPassword();
            if (userPass == hashedPass) {
                Optional<Session> optionalSession = sessions.stream().filter(sess -> sess.getUser().equals(user)).findFirst();
                if (!optionalSession.isPresent()) {
                    String token = UUID.randomUUID().toString();
                    Session session = Session.builder()
                            .withExpireDate(LocalDateTime.now().plusSeconds(timeToLive))
                            .withToken(token)
                            .withUser(user)
                            .withCart(new ArrayList<>())
                            .build();
                    sessions.add(session);
                    logger.info("Session for user {} created", user.getLogin());
                    return Optional.of(session);
                }
                return optionalSession;
            }
        }
        return Optional.empty();
    }

    @Override
    public long getSessionTimeToLive(Session session) {
        LocalDateTime expireDate = session.getExpireDate();
        return LocalDateTime.now().until(expireDate, ChronoUnit.SECONDS);
    }

    @Override
    public void logout(AuthPrincipal authPrincipal) {
        sessions.removeIf(session -> session.getUser().equals(authPrincipal.getUser()));
    }

    @Override
    public Optional<Session> getSessionByToken(String token) {
        Optional<Session> sessionByToken= sessions
                .stream()
                .filter(session -> session.getToken().equals(token))
                .findFirst();
        if (sessionByToken.isPresent()) {
            Session session = sessionByToken.get();
            if (session.getExpireDate().isBefore(LocalDateTime.now())) {
                sessions.remove(session);
                logger.info("Session with token {} is expired and was removed", token);
                return Optional.empty();
            } else {
                logger.info("Session with token {} is valid", token);
                return sessionByToken;
            }
        }
        return Optional.empty();
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }
}