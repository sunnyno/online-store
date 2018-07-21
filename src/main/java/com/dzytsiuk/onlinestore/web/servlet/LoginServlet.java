package com.dzytsiuk.onlinestore.web.servlet;

import com.dzytsiuk.onlinestore.security.SecurityService;
import com.dzytsiuk.onlinestore.security.Session;
import com.dzytsiuk.onlinestore.web.templater.PageProcessor;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class LoginServlet extends HttpServlet {
    private static final String LOGIN_PARAMETER_NAME = "login";
    private static final String PASSWORD_PARAMETER_NAME = "password";
    private static final String USER_TOKEN_PARAMETER_NAME = "user-token";
    private SecurityService securityService;


    public LoginServlet(SecurityService securityService) {
        this.securityService = securityService;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PageProcessor.instance().process("login.html", new WebContext(req, resp, req.getServletContext()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter(LOGIN_PARAMETER_NAME);
        String password = req.getParameter(PASSWORD_PARAMETER_NAME);
        Optional<Session> optionalSession = securityService.auth(login, password);
        optionalSession.ifPresent(token -> {
            Cookie cookie = new Cookie(USER_TOKEN_PARAMETER_NAME, optionalSession.get().getToken());
            int secondsToExpire = securityService.getSessionTimeToLive();
            cookie.setMaxAge(secondsToExpire);
            cookie.setHttpOnly(true);
            resp.addCookie(cookie);
            try {
                resp.sendRedirect("/products");
            } catch (IOException e) {
                throw new RuntimeException("Redirection failed", e);
            }
        });
        WebContext webContext = new WebContext(req, resp, req.getServletContext());
        webContext.setVariable("errorMessage", "Invalid login/password");
        PageProcessor.instance().process("login.html", webContext);
    }
}
