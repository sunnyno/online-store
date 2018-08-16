package com.dzytsiuk.onlinestore.web.servlet;

import com.dzytsiuk.onlinestore.security.SecurityService;
import com.dzytsiuk.onlinestore.security.Session;
import com.dzytsiuk.onlinestore.service.ProductService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class DeleteCartItemServlet extends HttpServlet {
    private SecurityService securityService;
    private ProductService productService;

    public DeleteCartItemServlet(SecurityService securityService, ProductService productService) {
        this.securityService = securityService;
        this.productService = productService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String productId = req.getParameter("productId");
        Optional<Session> currentSession = securityService.getCurrentSession(req);
        currentSession.ifPresent(session ->
                session.getProducts().remove(productService.findProductById(Integer.parseInt(productId))));
        resp.sendRedirect("/cart");
    }
}
