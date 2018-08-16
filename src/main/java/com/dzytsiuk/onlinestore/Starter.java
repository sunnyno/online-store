package com.dzytsiuk.onlinestore;

import com.dzytsiuk.onlinestore.property.PropertyContainer;
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
import java.util.Properties;

public class Starter {
    private static final String SSLFACTORY = "web.sslfactory";
    private static final String SSL = "web.ssl";
    private static final String PORT = "port";

    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("context.xml");
        Properties properties = PropertyContainer.getProperties();
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
        context.addServlet(new ServletHolder(new CartServlet(securityService, productService)), "/cart");
        context.addServlet(new ServletHolder(new DeleteCartItemServlet(securityService, productService)), "/cart/delete");
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
}
