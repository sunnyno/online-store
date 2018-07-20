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
    private static final String PROPERTIES_PATH_PARAMETER_NAME = "properties.path";
    private static final String PLACEHOLDER_PREFIX = "${";


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        String[] beanDefinitionNames = configurableListableBeanFactory.getBeanDefinitionNames();

        String propertiesPath = System.getProperty(PROPERTIES_PATH_PARAMETER_NAME);
        try (InputStream resourceAsStream = getClass().getResourceAsStream(propertiesPath)) {
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
                            if (realValue.startsWith(PLACEHOLDER_PREFIX)) {
                                String substring = realValue.substring(realValue.indexOf('{')+1,
                                        realValue.indexOf('}'));
                                System.out.println(substring);
                                realValue = System.getenv().get(substring);
                            }
                            propertyValues.addPropertyValue(new PropertyValue(key, realValue));
                        }
                    }
                }
            });
        } catch (IOException e) {
            throw new RuntimeException("Error getting properties", e);
        }

    }
}