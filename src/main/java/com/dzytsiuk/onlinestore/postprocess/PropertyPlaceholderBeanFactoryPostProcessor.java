package com.dzytsiuk.onlinestore.postprocess;

import com.dzytsiuk.onlinestore.property.PropertyContainer;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.TypedStringValue;

import java.util.Arrays;
import java.util.Properties;

public class PropertyPlaceholderBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    private static final String PLACEHOLDER_PREFIX = "${";
    private static final char PLACEHOLDER_SUFFIX = '}';

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        Properties properties = PropertyContainer.getProperties();
        String[] beanDefinitionNames = configurableListableBeanFactory.getBeanDefinitionNames();
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
                            String propertyValueName = contextValue.substring(contextValue.indexOf(PLACEHOLDER_PREFIX) + PLACEHOLDER_PREFIX.length(),
                                    contextValue.indexOf(PLACEHOLDER_SUFFIX));
                            replacingValue = System.getenv().get(propertyValueName);
                        }
                        propertyValues.addPropertyValue(key, replacingValue);
                    }
                }
            }
        });
    }
}