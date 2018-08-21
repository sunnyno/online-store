package com.dzytsiuk.onlinestore.web.controller;

import com.dzytsiuk.onlinestore.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Controller
public class LogoutController {
    private static final String USER_TOKEN_COOKIE = "user-token";

    private SecurityService securityService;

    @Autowired
    public LogoutController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public void doLogout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Cookie[] cookies = req.getCookies();
        Optional<Cookie> tokenCookie = Optional.empty();
        if (cookies != null) {
            tokenCookie = Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals(USER_TOKEN_COOKIE))
                    .findFirst();
        }
        tokenCookie.ifPresent(cookie -> {
            securityService.logout(cookie.getValue());
            cookie.setMaxAge(0);
            resp.addCookie(cookie);
        });
        resp.sendRedirect("/login");
    }
}
