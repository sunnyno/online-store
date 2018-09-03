package com.dzytsiuk.onlinestore.web.controller;

import com.dzytsiuk.onlinestore.entity.Product;
import com.dzytsiuk.onlinestore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public String showProducts(ModelMap model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "products.html";
    }

    @RequestMapping(value = "/product/add", method = RequestMethod.GET)
    public String showAddProductForm() {
        return "addProduct.html";
    }

    @RequestMapping(value = "/product/add", method = RequestMethod.POST)
    public String addProductForm(@RequestParam String name, @RequestParam Double price) {
        productService.save(new Product(name, price));
        return "redirect:/products";
    }
}
