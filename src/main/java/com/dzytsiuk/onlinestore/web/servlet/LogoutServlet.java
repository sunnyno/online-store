package com.dzytsiuk.onlinestore.web.servlet;

import com.dzytsiuk.onlinestore.security.SecurityService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class LogoutServlet extends HttpServlet {
    private static final String USER_TOKEN_COOKIE = "user-token";
    private SecurityService securityService;

    public LogoutServlet(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Cookie[] cookies = req.getCookies();
        Optional<Cookie> tokenCookie = Optional.empty();
        if (cookies != null) {
            tokenCookie = Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals(USER_TOKEN_COOKIE))
                    .findFirst();
        }
        tokenCookie.ifPresent(cookie -> {
            securityService.getSessions().removeIf(session -> session.getToken().equals(cookie.getValue()));
            cookie.setMaxAge(0);
            resp.addCookie(cookie);
        });
        resp.sendRedirect("/login");
    }
}
