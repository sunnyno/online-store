package com.dzytsiuk.onlinestore.postprocess;

import com.dzytsiuk.ioc.context.postprocessing.BeanPostProcessor;
import org.apache.commons.dbcp.BasicDataSource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.dzytsiuk.onlinestore.Starter.ENV;
import static com.dzytsiuk.onlinestore.Starter.PROD;

public class SslPropertyPostProcessor implements BeanPostProcessor {

    private static final String ADD_CONNECTION_PROPERTY_METHOD = "addConnectionProperty";
    private static final String SSLFACTORY = "sslfactory";
    private static final String SSL_FACTORY_VALUE = "org.postgresql.ssl.NonValidatingFactory";
    private static final String SSL = "ssl";


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        Class<?> clazz = bean.getClass();
        String envProperty = System.getProperty(ENV);
        if (envProperty != null && envProperty.equalsIgnoreCase(PROD) && BasicDataSource.class.isAssignableFrom(clazz)){
            try {
                Method addConnectionPropertyMethod = clazz.getMethod(ADD_CONNECTION_PROPERTY_METHOD, String.class, String.class);
                addConnectionPropertyMethod.invoke(bean, SSLFACTORY, SSL_FACTORY_VALUE);
                addConnectionPropertyMethod.invoke(bean, SSL, String.valueOf(true));
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("No SSL setup method "+ ADD_CONNECTION_PROPERTY_METHOD, e);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("Error setting SSL properties", e);
            }
        }
            return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }
}
