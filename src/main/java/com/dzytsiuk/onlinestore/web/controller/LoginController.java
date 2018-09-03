package com.dzytsiuk.onlinestore.web.controller;

import com.dzytsiuk.onlinestore.security.SecurityService;
import com.dzytsiuk.onlinestore.entity.security.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Controller
public class LoginController {
    private static final String LOGIN_PARAMETER_NAME = "login";
    private static final String PASSWORD_PARAMETER_NAME = "password";
    private static final String USER_TOKEN_PARAMETER_NAME = "user-token";
    private SecurityService securityService;

    @Autowired
    public LoginController(SecurityService securityService) {
        this.securityService = securityService;
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginPage() {
        return "login.html";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String doLogin(HttpServletRequest req, HttpServletResponse resp, ModelMap modelMap) {
        String login = req.getParameter(LOGIN_PARAMETER_NAME);
        String password = req.getParameter(PASSWORD_PARAMETER_NAME);
        Optional<Session> optionalSession = securityService.auth(login, password);
        optionalSession.ifPresent(token -> {
            Cookie cookie = new Cookie(USER_TOKEN_PARAMETER_NAME, optionalSession.get().getToken());
            long secondsToExpire = securityService.getSessionTimeToLive(optionalSession.get());
            cookie.setMaxAge(Math.toIntExact(secondsToExpire));
            cookie.setHttpOnly(true);
            resp.addCookie(cookie);
            try {
                resp.sendRedirect("/products");
            } catch (IOException e) {
                throw new RuntimeException("Redirection failed", e);
            }
        });

        modelMap.addAttribute("errorMessage", "Invalid login/password");
        return "login.html";
    }
}
