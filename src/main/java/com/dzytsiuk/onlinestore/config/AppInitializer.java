package com.dzytsiuk.onlinestore.config;

import com.dzytsiuk.onlinestore.web.filter.SecurityFilter;
import com.dzytsiuk.onlinestore.web.filter.Utf8Filter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.EnumSet;

public class AppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) {
        //root context
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(AppProdConfig.class);
        rootContext.register(AppDevConfig.class);

        //root context listener
        servletContext.addListener(new ContextLoaderListener(rootContext));

        //web context
        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        webContext.register(WebConfig.class);

        //filters
        servletContext.addFilter("utf8Filter", Utf8Filter.class)
                .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD),
                        false, "/*");
        servletContext.addFilter("securityFilter", SecurityFilter.class)
                .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD),
                        false, "/*");

        //dispatcher servlet
        DispatcherServlet servlet = new DispatcherServlet(webContext);
        ServletRegistration.Dynamic registration = servletContext.addServlet("/", servlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/*");
    }
}
