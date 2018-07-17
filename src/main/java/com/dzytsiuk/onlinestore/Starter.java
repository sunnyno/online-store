package com.dzytsiuk.onlinestore;

import com.dzytsiuk.ioc.context.ApplicationContext;
import com.dzytsiuk.ioc.context.ClassPathApplicationContext;
import com.dzytsiuk.onlinestore.security.SecurityService;
import com.dzytsiuk.onlinestore.service.ProductService;
import com.dzytsiuk.onlinestore.web.filter.SecurityFilter;
import com.dzytsiuk.onlinestore.web.filter.Utf8Filter;
import com.dzytsiuk.onlinestore.web.servlet.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class Starter {
    public static final String ENV = "env";
    public static final String PROD = "PROD";
    private static final String PORT_PARAMETER = "PORT";
    private static final int DEFAULT_PORT = 8080;


    public static void main(String[] args) throws Exception {
        String contextFile =Starter.class.getClassLoader().getResource("context.xml").getPath();
        ApplicationContext applicationContext = new ClassPathApplicationContext(contextFile);

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
