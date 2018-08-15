package com.dzytsiuk.onlinestore.web.servlet;


import com.dzytsiuk.onlinestore.entity.Product;
import com.dzytsiuk.onlinestore.service.ProductService;
import com.dzytsiuk.onlinestore.web.templater.PageProcessor;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ProductServlet extends HttpServlet {
    private ProductService productService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Product> products = productService.findAll();
        WebContext webContext = new WebContext(request, response, request.getServletContext());
        webContext.setVariable("products", products);
        response.setStatus(HttpServletResponse.SC_OK);
        PageProcessor.instance().process("products.html", webContext);
    }


    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
