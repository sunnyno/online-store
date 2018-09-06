package com.dzytsiuk.onlinestore.config;

import com.dzytsiuk.onlinestore.web.filter.SecurityFilter;
import com.dzytsiuk.onlinestore.web.filter.Utf8Filter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.EnumSet;

public class AppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(AppProdConfig.class);
        rootContext.register(AppDevConfig.class);

        servletContext.addListener(new ContextLoaderListener(rootContext));

        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        webContext.register(WebConfig.class);
        DispatcherServlet servlet = new DispatcherServlet(webContext);
        servletContext.addFilter("utf8Filter", Utf8Filter.class)
                .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD),
                        false, "/*");
        servletContext.addFilter("securityFilter", SecurityFilter.class)
                .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD),
                        false, "/*");
        ServletRegistration.Dynamic registration = servletContext.addServlet("/", servlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/*");
    }
}
