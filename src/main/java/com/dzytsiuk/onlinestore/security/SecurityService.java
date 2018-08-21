package com.dzytsiuk.onlinestore.security;

import com.dzytsiuk.onlinestore.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface SecurityService {

    boolean isValid(String token);

    Optional<Session> auth(String login, String password);

    void setUserService(UserService userService);

    long getSessionTimeToLive(Session session);

    void logout(String token);

    Optional<Session> getSessionByToken(String token);

    Optional<Session> getCurrentSession(HttpServletRequest req);
}