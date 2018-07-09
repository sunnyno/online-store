package com.dzytsiuk.onlinestore;

import com.dzytsiuk.onlinestore.dao.ProductDao;
import com.dzytsiuk.onlinestore.dao.UserDao;
import com.dzytsiuk.onlinestore.dao.jdbc.DataSourceManager;
import com.dzytsiuk.onlinestore.dao.jdbc.JdbcProductDao;
import com.dzytsiuk.onlinestore.dao.jdbc.JdbcUserDao;
import com.dzytsiuk.onlinestore.service.DefaultProductService;
import com.dzytsiuk.onlinestore.service.DefaultUserService;
import com.dzytsiuk.onlinestore.service.ProductService;
import com.dzytsiuk.onlinestore.security.SecurityService;
import com.dzytsiuk.onlinestore.service.UserService;
import com.dzytsiuk.onlinestore.web.filter.SecurityFilter;
import com.dzytsiuk.onlinestore.web.filter.Utf8Filter;
import com.dzytsiuk.onlinestore.web.servlet.*;
import org.apache.commons.dbcp.BasicDataSource;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class Starter {
    private static final String PORT_PARAMETER = "PORT";
    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) throws Exception {
        //data source
        BasicDataSource dataSource = new DataSourceManager().getDataSource();

        //dao
        ProductDao productDao = new JdbcProductDao(dataSource);
        UserDao userDao = new JdbcUserDao(dataSource);

        //service
        ProductService productService = new DefaultProductService();
        productService.setProductDao(productDao);
        UserService userService = new DefaultUserService();
        userService.setUserDao(userDao);
        SecurityService securityService = new SecurityService();
        securityService.setUserService(userService);


        //servlet
        ProductServlet productServlet = new ProductServlet();
        productServlet.setProductService(productService);
        AddProductServlet addProductServlet = new AddProductServlet();
        addProductServlet.setProductService(productService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(productServlet), "/products");
        context.addServlet(new ServletHolder(addProductServlet), "/product/add");
        context.addServlet(new ServletHolder(new AssetsServlet()), "/assets/*");
        context.addServlet(new ServletHolder(new LoginServlet(securityService)), "/login");
        context.addServlet(new ServletHolder(new LogoutServlet(securityService)), "/logout");

        //filter
        context.addFilter(new FilterHolder(new SecurityFilter(securityService)), "/*", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(new FilterHolder(new Utf8Filter()), "/*", EnumSet.of(DispatcherType.REQUEST));

        //port
        int port = DEFAULT_PORT;
        String systemPort = System.getenv().get(PORT_PARAMETER);
        if (systemPort != null) {
            port = Integer.parseInt(systemPort);
        }

        //start
        Server server = new Server(port);
        server.setHandler(context);
        server.start();
    }


}
