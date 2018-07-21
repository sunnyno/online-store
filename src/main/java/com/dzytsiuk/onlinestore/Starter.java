package com.dzytsiuk.onlinestore;

import com.dzytsiuk.onlinestore.security.SecurityService;
import com.dzytsiuk.onlinestore.service.ProductService;
import com.dzytsiuk.onlinestore.web.filter.SecurityFilter;
import com.dzytsiuk.onlinestore.web.filter.Utf8Filter;
import com.dzytsiuk.onlinestore.web.servlet.*;
import org.apache.commons.dbcp.BasicDataSource;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.DispatcherType;
import javax.sql.DataSource;
import java.util.EnumSet;

public class Starter {
    private static final String PORT_PARAMETER = "PORT";
    private static final int DEFAULT_PORT = 8080;
    private static final String SSLFACTORY = "sslfactory";
    private static final String SSL_FACTORY_VALUE = "org.postgresql.ssl.NonValidatingFactory";
    private static final String SSL = "ssl";


    public static void main(String[] args) throws Exception {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("context.xml");

        BasicDataSource dataSource = (BasicDataSource) applicationContext.getBean(DataSource.class);
        dataSource.addConnectionProperty(SSLFACTORY, SSL_FACTORY_VALUE);
        dataSource.addConnectionProperty(SSL, String.valueOf(true));

        //service
        ProductService productService = applicationContext.getBean(ProductService.class);
        SecurityService securityService = applicationContext.getBean(SecurityService.class);

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
