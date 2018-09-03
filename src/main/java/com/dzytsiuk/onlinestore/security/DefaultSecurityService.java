package com.dzytsiuk.onlinestore.security;

import com.dzytsiuk.onlinestore.entity.User;
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
    private Map<User, Session> sessions = new HashMap<>();
    @Value("${session.ttl}")
    private long timeToLive;

    @Autowired
    public DefaultSecurityService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String token) {
        logger.debug("Start validation of session with token {}", token);
        Optional<Session> sessionByToken = getSessionByToken(token);
        if (sessionByToken.isPresent()) {
            Session session = sessionByToken.get();
            if (session.getExpireDate().isBefore(LocalDateTime.now())) {
                sessions.remove(session.getUser());
                logger.info("Session with token {} is expired and was removed", token);
                return false;
            } else {
                logger.info("Session with token {} is valid", token);
                return true;
            }
        }
        return false;
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
                Session session = sessions.get(user);
                if (session == null) {
                    String token = UUID.randomUUID().toString();
                    session = Session.builder()
                            .withExpireDate(LocalDateTime.now().plusSeconds(timeToLive))
                            .withToken(token)
                            .withUser(user)
                            .withCart(new ArrayList<>())
                            .build();
                    sessions.put(user, session);
                    logger.info("Session for user {} created", user.getLogin());
                }
                return Optional.of(session);
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
    public void logout(String token) {
        Optional<Session> sessionByToken = getSessionByToken(token);
        sessionByToken.ifPresent(session -> {
            sessions.remove(session.getUser());
            logger.info("User {} was logged out", session.getUser());
        });
    }

    @Override
    public Optional<Session> getSessionByToken(String token) {
        return sessions.values().stream().filter(session -> session.getToken().equals(token)).findFirst();
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }
}