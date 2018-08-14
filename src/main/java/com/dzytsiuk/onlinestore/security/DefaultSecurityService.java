package com.dzytsiuk.onlinestore.security;

import com.dzytsiuk.onlinestore.entity.User;
import com.dzytsiuk.onlinestore.service.UserService;

import java.time.LocalDateTime;
import java.util.*;

public class DefaultSecurityService implements SecurityService {
    private UserService userService;
    private List<Session> sessions = new ArrayList<>();
    private int ttl;

    @Override
    public boolean isValid(String token) {
        return sessions.stream()
                .filter(x -> x.getToken().equals(token))
                .anyMatch(x -> {
                    if (x.getExpireDate().isBefore(LocalDateTime.now())) {
                        sessions.remove(x);
                        return false;
                    } else {
                        return true;
                    }
                });
    }

    @Override
    public Optional<Session> auth(String login, String password) {
        Optional<User> optionalUser = userService.findByLogin(login);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            int hashedPass = Objects.hash(password, user.getSalt());
            int userPass = user.getPassword();
            if (userPass == hashedPass) {
                Session session = new Session();
                String token = UUID.randomUUID().toString();
                session.setUser(user);
                session.setToken(token);
                session.setExpireDate(LocalDateTime.now().plusSeconds(ttl));
                sessions.add(session);
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
    public List<Session> getSessions() {
        return sessions;
    }

    @Override
    public int getSessionTimeToLive() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }
}
