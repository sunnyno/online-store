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
    private String propertyPath;


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        String[] beanDefinitionNames = configurableListableBeanFactory.getBeanDefinitionNames();
        System.out.println(propertyPath);
        try (InputStream resourceAsStream = getClass().getResourceAsStream(propertyPath)) {
            Properties properties = new Properties();
            properties.load(resourceAsStream);
            Arrays.stream(beanDefinitionNames).forEach(beanDefinitionName ->
            {
                BeanDefinition beanDefinition = configurableListableBeanFactory.getBeanDefinition(beanDefinitionName);
                MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();
                for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                    Object value = propertyValue.getValue();
                    if (value instanceof TypedStringValue) {
                        TypedStringValue typedStringValue = (TypedStringValue) value;
                        String contextValue = typedStringValue.getValue();
                        if (contextValue.startsWith(PLACEHOLDER_PREFIX)) {
                            String key = propertyValue.getName();
                            String realValue = properties.getProperty(key);
                            //if no value found in property file look into system properties
                            if (realValue == null) {
                                String substring = contextValue.substring(contextValue.indexOf('{')+1,
                                        contextValue.indexOf('}'));
                                System.out.println(substring);
                                realValue = System.getenv().get(substring);
                            }
                            propertyValues.addPropertyValue(key, realValue);
                        }
                    }
                }
            });
        } catch (IOException e) {
            throw new RuntimeException("Error getting properties", e);
        }

    }

    public void setPropertyPath(String propertyPath) {
        this.propertyPath = propertyPath;
    }

}