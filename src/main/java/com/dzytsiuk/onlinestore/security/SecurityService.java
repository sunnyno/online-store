
package com.dzytsiuk.onlinestore.security;

import java.util.Optional;

public interface SecurityService {

    boolean isValid(String token);

    Optional<Session> auth(String login, String password);

    long getSessionTimeToLive(Session session);

    void logout(String token);

    Optional<Session> getSessionByToken(String token);
}