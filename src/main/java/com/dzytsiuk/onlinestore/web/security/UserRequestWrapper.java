package com.dzytsiuk.onlinestore.web.security;

import com.dzytsiuk.onlinestore.entity.security.AuthPrincipal;
import com.dzytsiuk.onlinestore.entity.security.Session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.security.Principal;

public class UserRequestWrapper extends HttpServletRequestWrapper {
    private Session session;

    public UserRequestWrapper(HttpServletRequest request, Session session) {
        super(request);
        this.session = session;
    }

    @Override
    public Principal getUserPrincipal() {
        return new AuthPrincipal(session);
    }
}
