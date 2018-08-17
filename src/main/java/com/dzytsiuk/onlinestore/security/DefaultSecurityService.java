package com.dzytsiuk.onlinestore.security;

import com.dzytsiuk.onlinestore.entity.User;
import com.dzytsiuk.onlinestore.security.builder.SessionBuilder;
import com.dzytsiuk.onlinestore.security.builder.SessionBuilderImpl;
import com.dzytsiuk.onlinestore.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class DefaultSecurityService implements SecurityService {
    private static final String USER_TOKEN_COOKIE = "user-token";
    private UserService userService;
    private Map<User, Session> sessions = new HashMap<>();
    private long timeToLive;

    @Override
    public boolean isValid(String token) {
        Optional<Session> sessionByToken = getSessionByToken(token);
        if (sessionByToken.isPresent()) {
            Session session = sessionByToken.get();
            if (session.getExpireDate().isBefore(LocalDateTime.now())) {
                sessions.remove(session.getUser());
                return false;
            }else{
                return true;
            }
        }
        return false;
    }

    @Override
    public Optional<Session> auth(String login, String password) {
        Optional<User> optionalUser = userService.findByLogin(login);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            int hashedPass = Objects.hash(password, user.getSalt());
            int userPass = user.getPassword();
            if (userPass == hashedPass) {
                Session session = sessions.get(user);
                if (session == null) {
                    String token = UUID.randomUUID().toString();
                    SessionBuilder sessionBuilder = new SessionBuilderImpl();
                    sessionBuilder.setToken(token);
                    sessionBuilder.setUser(user);
                    sessionBuilder.setExpireDate(LocalDateTime.now().plusSeconds(timeToLive));
                    sessionBuilder.setCartItems(new ArrayList<>());
                    session = sessionBuilder.getSession();
                    sessions.put(user, session);
                }
                return Optional.of(session);
            }
        }
        return Optional.empty();
    }

    @Override
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public long getSessionTimeToLive(Session session) {
        LocalDateTime expireDate = session.getExpireDate();
        return LocalDateTime.now().until(expireDate, ChronoUnit.SECONDS);
    }

    @Override
    public void logout(String token) {
        Optional<Session> sessionByToken = getSessionByToken(token);
        sessionByToken.ifPresent(session -> sessions.remove(session.getUser()));
    }

    @Override
    public Optional<Session> getSessionByToken(String token) {
        return sessions.values().stream().filter(session -> session.getToken().equals(token)).findFirst();
    }

    @Override
    public Optional<Session> getCurrentSession(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        Optional<Cookie> tokenCookie = Optional.empty();
        if (cookies != null) {
            tokenCookie = Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals(USER_TOKEN_COOKIE))
                    .findFirst();
        }
        if (tokenCookie.isPresent()) {
            return getSessionByToken(tokenCookie.get().getValue());
        }
        return Optional.empty();
    }
    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }
}
