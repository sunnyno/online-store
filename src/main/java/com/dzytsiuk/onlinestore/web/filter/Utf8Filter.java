package com.dzytsiuk.onlinestore.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class Utf8Filter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        if(!httpServletRequest.getRequestURI().contains("/assets/")) {
            servletResponse.setContentType("text/html;charset=utf-8");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }


    @Override
    public void destroy() {

    }
}
