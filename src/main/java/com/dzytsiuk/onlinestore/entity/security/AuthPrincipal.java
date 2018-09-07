package com.dzytsiuk.onlinestore.entity.security;

import com.dzytsiuk.onlinestore.entity.User;

import java.security.Principal;

public class AuthPrincipal implements Principal {
    private Session session;

    //for tests
    public AuthPrincipal() {
    }

    public AuthPrincipal(Session session) {
        this.session = session;
    }

    @Override
    public String getName() {
        return session.getUser().getLogin();
    }

    public Session getSession() {
        return session;
    }

    public User getUser() {
        return session.getUser();
    }
}
