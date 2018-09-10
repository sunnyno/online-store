package com.dzytsiuk.onlinestore.web.controller;

import com.dzytsiuk.onlinestore.entity.CartItem;
import com.dzytsiuk.onlinestore.entity.security.AuthPrincipal;
import com.dzytsiuk.onlinestore.entity.security.Session;
import com.dzytsiuk.onlinestore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CartController {

    private final ProductService productService;

    @Autowired
    public CartController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String showCart(AuthPrincipal authPrincipal, ModelMap modelMap) {
        Session session = authPrincipal.getSession();
        List<CartItem> cartItems = session.getCartItems();
        modelMap.addAttribute("cartItems", cartItems);
        return "cart.html";
    }

    @RequestMapping(value = "/cart/{id}", method = RequestMethod.POST)
    public String addToCart(@PathVariable int id, AuthPrincipal authPrincipal) {
        Session session = authPrincipal.getSession();
        session.addProduct(productService.findProductById(id));
        return "products.html";
    }

    @RequestMapping(value = "/cart/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteFromCart(@PathVariable int id, AuthPrincipal authPrincipal) {
        Session session = authPrincipal.getSession();
        session.removeProduct(productService.findProductById(id));
    }
}
