
package com.dzytsiuk.onlinestore.security;

import com.dzytsiuk.onlinestore.entity.security.AuthPrincipal;
import com.dzytsiuk.onlinestore.entity.security.Session;

import java.util.Optional;

public interface SecurityService {

    Optional<Session> auth(String login, String password);

    long getSessionTimeToLive(Session session);

    void logout(AuthPrincipal authPrincipal);

    Optional<Session> getSessionByToken(String token);
}