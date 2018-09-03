package com.dzytsiuk.onlinestore.web.controller;

import com.dzytsiuk.onlinestore.entity.CartItem;
import com.dzytsiuk.onlinestore.security.SecurityService;
import com.dzytsiuk.onlinestore.entity.security.Session;
import com.dzytsiuk.onlinestore.service.ProductService;
import com.dzytsiuk.onlinestore.web.util.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class CartController {

    private final SecurityService securityService;
    private final ProductService productService;

    @Autowired
    public CartController(SecurityService securityService, ProductService productService) {
        this.securityService = securityService;
        this.productService = productService;
    }

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String showCart(HttpServletRequest req, ModelMap modelMap) {
        Optional<Cookie> optionalCookie = WebUtil.getCurrentSessionCookie(req);
        optionalCookie.ifPresent(cookie -> {
            Optional<Session> currentSession = securityService.getSessionByToken(cookie.getValue());
            currentSession.ifPresent(session -> {
                List<CartItem> cartItems = session.getCartItems();
                modelMap.addAttribute("cartItems", cartItems);
            });
        });

        return "cart.html";
    }

    @RequestMapping(value = "/cart", method = RequestMethod.POST)
    public void addToCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String productId = req.getParameter("productId");
        Optional<Cookie> optionalCookie = WebUtil.getCurrentSessionCookie(req);
        optionalCookie.ifPresent(cookie -> {
            Optional<Session> currentSession = securityService.getSessionByToken(cookie.getValue());
            currentSession.ifPresent(session ->
                    session.addProduct(productService.findProductById(Integer.parseInt(productId))));
        });

        resp.sendRedirect("/products");
    }

    @RequestMapping(value = "/cart/delete", method = RequestMethod.POST)
    public void deleteFromCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String productId = req.getParameter("productId");
        Optional<Cookie> optionalCookie = WebUtil.getCurrentSessionCookie(req);
        optionalCookie.ifPresent(cookie -> {
            Optional<Session> currentSession = securityService.getSessionByToken(cookie.getValue());
            currentSession.ifPresent(session ->
                    session.removeProduct(productService.findProductById(Integer.parseInt(productId))));
        });
        resp.sendRedirect("/cart");
    }
}
