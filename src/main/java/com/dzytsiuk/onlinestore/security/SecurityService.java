package com.dzytsiuk.onlinestore.security;

import com.dzytsiuk.onlinestore.service.UserService;

import java.util.List;

public interface SecurityService {
    void addSession(Session session);

    boolean isValid(String token);

    Session auth(String login, String password);

    void setUserService(UserService userService);

    List<Session> getSessions();

}
