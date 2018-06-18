package com.dzytsiuk.onlinestore.web.templater;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;


public class PageGenerator {
    private static final String HTML_DIR = "/template/product/";

    private static PageGenerator pageGenerator;
    private final Configuration cfg;

    public static PageGenerator instance() {
        if (pageGenerator == null)
            pageGenerator = new PageGenerator();

        return pageGenerator;
    }

    public String getPage(String filename, Map<String, Object> data) {
        Writer stream = new StringWriter();
        try {

            cfg.setClassForTemplateLoading(getClass(), HTML_DIR);
            Template template = cfg.getTemplate(filename);

            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
        return stream.toString();
    }

    private PageGenerator() {
        cfg = new Configuration();
    }

    public String getPage(String filename) {
        return getPage(filename, new HashMap<>());
    }


}
