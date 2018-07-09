package com.dzytsiuk.onlinestore.web.servlet;

import com.dzytsiuk.onlinestore.security.SecurityService;
import com.dzytsiuk.onlinestore.security.Session;
import com.dzytsiuk.onlinestore.web.templater.PageGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {
    private static final String LOGIN_PARAMETER_NAME = "login";
    private static final String PASSWORD_PARAMETER_NAME = "password";
    private SecurityService securityService;


    public LoginServlet(SecurityService securityService) {
        this.securityService = securityService;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().println(PageGenerator.instance().getPage("login.html"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter(LOGIN_PARAMETER_NAME);
        String password = req.getParameter(PASSWORD_PARAMETER_NAME);
        Session session = securityService.auth(login, password);

        if (session.getToken() == null) {
            Map<String, Object> responseMessage = new HashMap<>();
            responseMessage.put("error", "Invalid login/password");
            resp.getWriter().println(PageGenerator.instance().getPage("login.html", responseMessage));
        } else {
            Cookie cookie = new Cookie("user-token", session.getToken());
            int secondsToExpire = (int) ChronoUnit.SECONDS.between(LocalDateTime.now(), session.getExpireDate());
            cookie.setMaxAge(secondsToExpire);
            cookie.setHttpOnly(true);
            resp.addCookie(cookie);
            securityService.addSession(session);
            resp.sendRedirect("/products");
        }
    }
}
