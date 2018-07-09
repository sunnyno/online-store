package com.dzytsiuk.onlinestore.security;

import com.dzytsiuk.onlinestore.entity.User;
import com.dzytsiuk.onlinestore.service.UserService;

import java.time.LocalDateTime;
import java.util.*;

public class SecurityService {
    private static final int DEFAULT_EXPIRE_SECONDS = 30000;

    private UserService userService;

    private List<Session> sessions = new ArrayList<>();


    public void addSession(Session session) {
        sessions.add(session);
    }

    public boolean isValid(String token) {
        return sessions.stream()
                .filter(x -> x.getToken().equals(token))
                .anyMatch(x -> x.getExpireDate().isAfter(LocalDateTime.now()));
    }

    public Session auth(String login, String password) {
        Session session = new Session();
        User user = userService.findByLogin(login);
        int hashedPass = Objects.hash(password, login);
        String userPass = user.getPassword();
        if (userPass != null && Integer.parseInt(userPass) == hashedPass) {
            String token = UUID.randomUUID().toString();
            session.setUser(user);
            session.setToken(token);
            session.setExpireDate(DEFAULT_EXPIRE_SECONDS);
        }
        return session;

    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public List<Session> getSessions() {
        return sessions;
    }

}
