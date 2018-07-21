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
import java.io.IOException;
import java.util.EnumSet;
import java.util.Properties;

public class Starter {
    private static final String SSLFACTORY = "sslfactory";
    private static final String SSL = "ssl";
    private static final String PORT = "port";

    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("context.xml");
        Properties properties = getEnvironmentProperties();
        //datasource ssl config
        String sslProperty = properties.getProperty(SSL);
        if (sslProperty != null) {
            BasicDataSource dataSource = (BasicDataSource) applicationContext.getBean(DataSource.class);
            dataSource.addConnectionProperty(SSL, sslProperty);
            dataSource.addConnectionProperty(SSLFACTORY, properties.getProperty(SSLFACTORY));
        }

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
        context.addFilter(new FilterHolder(new SecurityFilter(securityService)), "/*", EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD));
        context.addFilter(new FilterHolder(new Utf8Filter()), "/*", EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD));

        //start
        String port = properties.getProperty(PORT);
        if(port == null){
            port = System.getenv().get(PORT.toUpperCase());
        }
        Server server = new Server(Integer.parseInt(port));
        server.setHandler(context);
        server.start();
    }

    private static Properties getEnvironmentProperties() {
        try {
            Properties properties = new Properties();
            properties.load(Starter.class.getResourceAsStream(System.getProperty("properties.path")));
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("Error loading environment properties", e);
        }
    }
}
