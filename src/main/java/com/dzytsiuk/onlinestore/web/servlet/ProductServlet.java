package com.dzytsiuk.onlinestore.web.servlet;


import com.dzytsiuk.onlinestore.entity.Product;
import com.dzytsiuk.onlinestore.service.ProductService;
import com.dzytsiuk.onlinestore.web.templater.PageGenerator;

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
        HashMap<String, Object> hashMap = new HashMap<>();
        List<Product> products = productService.getAll();
        hashMap.put("products", products);
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(PageGenerator.instance().getPage("products.html", hashMap));

    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
