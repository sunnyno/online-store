package com.dzytsiuk.onlinestore.dao.jdbc;

import org.postgresql.ds.PGSimpleDataSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DataSourceManager {

    private static final String ENV_PROPERTIES = "properties";
    private static final String HOST = "host";
    private static final String PORT = "port";
    private static final String DATABASE = "database";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String SSL = "ssl";
    private static final String SSLFACTORY = "sslfactory";
    private static final String DBCONFIG_DIR = "src/main/resources/dbconfig";
    public static final String DEFAULT_APPLICATION_PROPERTY_FILE = "dev.application.properties";

    public PGSimpleDataSource getPgSimpleDataSource() {
        try {
            Properties properties = new Properties();
            String propertyFile = System.getProperty(ENV_PROPERTIES);

            properties.load(new FileInputStream(new File(DBCONFIG_DIR,
                    propertyFile == null ? DEFAULT_APPLICATION_PROPERTY_FILE : propertyFile)));
            PGSimpleDataSource dataSource = new PGSimpleDataSource();
            dataSource.setServerName(properties.getProperty(HOST));
            dataSource.setPortNumber(Integer.parseInt(properties.getProperty(PORT)));
            dataSource.setDatabaseName(properties.getProperty(DATABASE));
            dataSource.setUser(properties.getProperty(USERNAME));
            dataSource.setPassword(properties.getProperty(PASSWORD));
            String ssl = properties.getProperty(SSL);
            if (ssl != null) {
                dataSource.setSsl(Boolean.parseBoolean(ssl));
                dataSource.setSslfactory(properties.getProperty(SSLFACTORY));
            }
            return dataSource;
        } catch (IOException e) {
            throw new RuntimeException("Error reading properties ", e);
        }
    }
}
