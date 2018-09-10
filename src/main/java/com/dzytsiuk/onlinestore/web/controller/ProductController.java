package com.dzytsiuk.onlinestore.web.controller;

import com.dzytsiuk.onlinestore.entity.Product;
import com.dzytsiuk.onlinestore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @ResponseBody
    public void addProduct(@RequestParam String name, @RequestParam Double price) {
        productService.save(new Product(name, price));
    }
}
