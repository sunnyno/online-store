package com.dzytsiuk.onlinestore.security;

import com.dzytsiuk.onlinestore.service.UserService;

import java.util.List;
import java.util.Optional;

public interface SecurityService {

    boolean isValid(String token);

    Optional<Session> auth(String login, String password);

    void setUserService(UserService userService);

    List<Session> getSessions();

    int getSessionTimeToLive();

}
