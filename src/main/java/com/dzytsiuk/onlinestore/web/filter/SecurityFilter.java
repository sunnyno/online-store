package com.dzytsiuk.onlinestore.web.filter;


import com.dzytsiuk.onlinestore.security.SecurityService;
import com.dzytsiuk.onlinestore.entity.security.Session;
import com.dzytsiuk.onlinestore.web.security.UserRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;


public class SecurityFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String USER_KEY = "username";

    private static final String LOGIN_URI = "/login";
    private static final String USER_TOKEN_COOKIE = "user-token";
    private static final String ASSETS_URI = "/assets/";

    private SecurityService securityService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if(!allowedPath(request.getRequestURI())) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                Optional<Cookie> optionalCookie = Arrays.stream(cookies)
                        .filter(cookie -> cookie.getName().equals(USER_TOKEN_COOKIE))
                        .findFirst();
                if (optionalCookie.isPresent()) {
                    Cookie cookie = optionalCookie.get();
                    processSession(filterChain, request, response, cookie);
                } else {
                    response.sendRedirect("/login");
                }
            }else{
                response.sendRedirect("/login");
            }
        }else{
            filterChain.doFilter(request, response);
        }
    }

    private void processSession(FilterChain filterChain, HttpServletRequest request, HttpServletResponse response, Cookie cookie) throws IOException, ServletException {
        Optional<Session> optionalSession = securityService.getSessionByToken(cookie.getValue());
        if (optionalSession.isPresent()) {
            Session session = optionalSession.get();
            MDC.put(USER_KEY, session.getUser().getLogin());
            try {
                filterChain.doFilter(new UserRequestWrapper(request, session), response);
            } finally {
                MDC.clear();
            }
        } else {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            response.sendRedirect("/login");
        }
    }

    private boolean allowedPath(String requestURI) {
        return LOGIN_URI.equals(requestURI) || requestURI.contains(ASSETS_URI);
    }

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void init(FilterConfig filterConfig) {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                filterConfig.getServletContext());
    }

    @Override
    public void destroy() {

    }
}
