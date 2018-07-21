package com.dzytsiuk.onlinestore.postprocess;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.TypedStringValue;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class PropertyPlaceholderBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    private static final String PLACEHOLDER_PREFIX = "${";
    private static final char PLACEHOLDER_SUFFIX = '}';
    private static final String PROPERTY_PATH_NAME = "properties.path";

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        String[] beanDefinitionNames = configurableListableBeanFactory.getBeanDefinitionNames();
        String propertyPath = System.getProperty(PROPERTY_PATH_NAME);
        try (InputStream propertyFile = getClass().getResourceAsStream(propertyPath)) {
            Properties properties = new Properties();
            properties.load(propertyFile);
            Arrays.stream(beanDefinitionNames).forEach(beanDefinitionName -> {
                BeanDefinition beanDefinition = configurableListableBeanFactory.getBeanDefinition(beanDefinitionName);
                MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();
                for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                    Object value = propertyValue.getValue();
                    if (value instanceof TypedStringValue) {
                        TypedStringValue typedStringValue = (TypedStringValue) value;
                        String contextValue = typedStringValue.getValue();
                        if (contextValue.startsWith(PLACEHOLDER_PREFIX)) {
                            String key = propertyValue.getName();
                            String replacingValue = properties.getProperty(key);
                            //unable to find property in a file thus look into system properties
                            if (replacingValue == null) {
                                String substring = contextValue.substring(contextValue.indexOf(PLACEHOLDER_PREFIX) + PLACEHOLDER_PREFIX.length(),
                                        contextValue.indexOf(PLACEHOLDER_SUFFIX));
                                replacingValue = System.getenv().get(substring);
                            }
                            propertyValues.addPropertyValue(key, replacingValue);
                        }
                    }
                }
            });
        } catch (IOException e) {
            throw new RuntimeException("Error getting properties", e);
        }

    }
}