package com.dzytsiuk.onlinestore.property;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyContainer {
    private static final String PROPERTY_PATH_NAME = "properties.path";
    private static Properties properties;

    public static Properties getProperties() {
        if (properties == null) {
            String propertyPath = System.getProperty(PROPERTY_PATH_NAME);
            try (InputStream propertyFile = PropertyContainer.class.getResourceAsStream(propertyPath)) {
                properties = new Properties();
                properties.load(propertyFile);
            } catch (IOException e) {
                throw new RuntimeException("Error getting properties", e);
            }
        }
        return properties;
    }
}