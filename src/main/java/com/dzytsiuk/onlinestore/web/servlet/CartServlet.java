package com.dzytsiuk.onlinestore.web.servlet;

import com.dzytsiuk.onlinestore.security.SecurityService;
import com.dzytsiuk.onlinestore.security.Session;
import com.dzytsiuk.onlinestore.service.ProductService;
import com.dzytsiuk.onlinestore.web.templater.PageProcessor;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class CartServlet extends HttpServlet {

    private SecurityService securityService;
    private ProductService productService;

    public CartServlet(SecurityService securityService, ProductService productService) {
        this.securityService = securityService;
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebContext webContext = new WebContext(req, resp, req.getServletContext());
        Optional<Session> currentSession = securityService.getCurrentSession(req);
        currentSession.ifPresent(session -> webContext.setVariable("cartItems", session.getCartItems()));
        PageProcessor.instance().process("cart.html", webContext);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String productId = req.getParameter("productId");
        Optional<Session> currentSession = securityService.getCurrentSession(req);
        currentSession.ifPresent(session ->
                session.addProduct(productService.findProductById(Integer.parseInt(productId))));
        resp.sendRedirect("/products");
    }

}
