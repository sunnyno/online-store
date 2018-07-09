package com.dzytsiuk.onlinestore.web.filter;


import com.dzytsiuk.onlinestore.security.SecurityService;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class SecurityFilter implements Filter {
    private static final String LOGIN_URI = "/login";
    private static final String USER_TOKEN_COOKIE = "user-token";
    private static final String ASSETS_URI = "/assets/";
    private SecurityService securityService;

    public SecurityFilter(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Cookie[] cookies = request.getCookies();
        boolean isAuth = false;
        if (cookies != null) {
            isAuth = Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals(USER_TOKEN_COOKIE))
                    .anyMatch(cookie -> securityService.isValid(cookie.getValue()));
        }

        String requestURI = request.getRequestURI();
        if (!isAuth && !requestURI.equals(LOGIN_URI) && !requestURI.contains(ASSETS_URI)) {
            response.sendRedirect(LOGIN_URI);
        } else {
            filterChain.doFilter(request, response);
        }
    }


    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void destroy() {

    }
}
