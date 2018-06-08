package com.dzytsiuk.onlinestore;

import com.dzytsiuk.onlinestore.dao.ProductDao;
import com.dzytsiuk.onlinestore.dao.jdbc.JdbcProductDao;
import com.dzytsiuk.onlinestore.service.ProductService;
import com.dzytsiuk.onlinestore.web.servlet.AddProductServlet;
import com.dzytsiuk.onlinestore.web.servlet.ProductServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Starter {

    public static void main(String[] args) throws Exception {
        ProductDao productDao = new JdbcProductDao();
        ProductService productService = new ProductService();
        productService.setProductDao(productDao);

        ProductServlet productServlet = new ProductServlet();
        productServlet.setProductService(productService);
        AddProductServlet addProductServlet = new AddProductServlet();
        addProductServlet.setProductService(productService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(productServlet), "/products");
        context.addServlet(new ServletHolder(addProductServlet), "/product/add");


        Server server = new Server(3000);
        server.setHandler(context);

        server.start();

    }

}
