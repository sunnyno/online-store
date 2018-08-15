package com.dzytsiuk.onlinestore.web.servlet;

import com.dzytsiuk.onlinestore.security.SecurityService;
import com.dzytsiuk.onlinestore.security.Session;
import com.dzytsiuk.onlinestore.service.ProductService;
import com.dzytsiuk.onlinestore.web.templater.PageProcessor;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class CartServlet extends HttpServlet {
    private static final String USER_TOKEN_COOKIE = "user-token";
    private SecurityService securityService;
    private ProductService productService;

    public CartServlet(SecurityService securityService, ProductService productService) {
        this.securityService = securityService;
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebContext webContext = new WebContext(req, resp, req.getServletContext());
        Optional<Session> currentSession = getCurrentSession(req);
        currentSession.ifPresent(session -> webContext.setVariable("products", session.getProducts()));
        PageProcessor.instance().process("cart.html", webContext);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String productId = req.getParameter("productId");
        Optional<Session> currentSession = getCurrentSession(req);
        currentSession.ifPresent(session ->
                session.addProduct(productService.findProductById(Integer.parseInt(productId))));
        resp.sendRedirect("/products");
    }

    private Optional<Session> getCurrentSession(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        Optional<Cookie> tokenCookie = Optional.empty();
        if (cookies != null) {
            tokenCookie = Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals(USER_TOKEN_COOKIE))
                    .findFirst();
        }
        if (tokenCookie.isPresent()) {
            return securityService.getSessionByToken(tokenCookie.get().getValue());
        }
        return Optional.empty();
    }
}
