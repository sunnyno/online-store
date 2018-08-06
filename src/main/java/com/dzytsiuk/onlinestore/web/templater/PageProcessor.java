package com.dzytsiuk.onlinestore.web.templater;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class PageProcessor {
    private static final String HTML_DIR = "template/product/";

    private static PageProcessor pageProcessor;

    public static PageProcessor instance() {
        if (pageProcessor == null)
            pageProcessor = new PageProcessor();
        return pageProcessor;
    }

    public void process(String filename, WebContext webContext) throws IOException {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix(HTML_DIR);
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.addDialect(new Java8TimeDialect());
        templateEngine.process(filename, webContext, webContext.getResponse().getWriter());
    }

}
