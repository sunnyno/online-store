package com.dzytsiuk.onlinestore.postprocess;

import com.dzytsiuk.ioc.context.postprocessing.BeanFactoryPostProcessor;
import com.dzytsiuk.ioc.entity.BeanDefinition;
import org.apache.commons.dbcp.BasicDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import static com.dzytsiuk.onlinestore.Starter.ENV;
import static com.dzytsiuk.onlinestore.Starter.PROD;

public class PropertyPlaceholderBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    private static final String DEV = "DEV";

    private static final String JDBC_DATABASE_PREFIX = "JDBC_DATABASE_";

    private static final String DBCONFIG_DIR = "/dbconfig/";
    private static final String DEV_APPLICATION_PROPERTY_FILE = "dev.application.properties";
    private static final String PLACEHOLDER_PREFIX = "${";


    @Override
    public void postProcessBeanFactory(List<BeanDefinition> beanDefinitionList) {
        beanDefinitionList.forEach(beanDefinition -> {
            String beanClassName = beanDefinition.getBeanClassName();
            try {
                Class<?> clazz = Class.forName(beanClassName);
                if (BasicDataSource.class.isAssignableFrom(clazz)) {
                    String env = System.getProperty(ENV);
                    if (env == null || env.equalsIgnoreCase(DEV)) {
                        setDevProperties(beanDefinition);
                    } else if (env.equalsIgnoreCase(PROD)) {
                        setProdProperties(beanDefinition);
                    }
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Class not found " + beanClassName);
            }

        });
    }

    private void setProdProperties(BeanDefinition beanDefinition) {
        beanDefinition.getDependencies().forEach((key, value) -> {
            if (value.startsWith(PLACEHOLDER_PREFIX)) {
                String propertyName = JDBC_DATABASE_PREFIX + key.toUpperCase();
                beanDefinition.getDependencies().put(key, System.getenv().get(propertyName));
            }
        });
    }

    private void setDevProperties(BeanDefinition beanDefinition) {
        try (InputStream resourceAsStream = getClass().getResourceAsStream(DBCONFIG_DIR + (
                DEV_APPLICATION_PROPERTY_FILE))) {
            Properties properties = new Properties();
            properties.load(resourceAsStream);
            beanDefinition.getDependencies().forEach((key, value) -> {
                if (value.startsWith(PLACEHOLDER_PREFIX)) {
                    beanDefinition.getDependencies().put(key, properties.getProperty(key));
                }
            });
        } catch (IOException e) {
            throw new RuntimeException("Error reading properties ", e);
        }
    }

}
