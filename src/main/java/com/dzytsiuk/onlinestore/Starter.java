package com.dzytsiuk.onlinestore;

import com.dzytsiuk.onlinestore.dao.ProductDao;
import com.dzytsiuk.onlinestore.dao.jdbc.DataSourceManager;
import com.dzytsiuk.onlinestore.dao.jdbc.JdbcProductDao;
import com.dzytsiuk.onlinestore.service.DefaultProductService;
import com.dzytsiuk.onlinestore.service.ProductService;
import com.dzytsiuk.onlinestore.web.servlet.AddProductServlet;
import com.dzytsiuk.onlinestore.web.servlet.AssetsServlet;
import com.dzytsiuk.onlinestore.web.servlet.ProductServlet;
import org.apache.commons.dbcp.BasicDataSource;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Starter {


    public static void main(String[] args) throws Exception {
        BasicDataSource dataSource = new DataSourceManager().getDataSource();

        ProductDao productDao = new JdbcProductDao(dataSource);
        ProductService productService = new DefaultProductService();
        productService.setProductDao(productDao);

        ProductServlet productServlet = new ProductServlet();
        productServlet.setProductService(productService);
        AddProductServlet addProductServlet = new AddProductServlet();
        addProductServlet.setProductService(productService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(productServlet), "/products");
        context.addServlet(new ServletHolder(addProductServlet), "/product/add");
        context.addServlet(new ServletHolder(new AssetsServlet()), "/assets/*");


        String systemPort = System.getProperty("port");
        int port = 8080;
        if (systemPort != null) {
            port = Integer.parseInt(systemPort);
        }
        Server server = new Server(port);
        server.setHandler(context);

        server.start();
    }


}
