package com.dzytsiuk.onlinestore.dao.jdbc;

import org.apache.commons.dbcp.BasicDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DataSourceManager {

    private static final String ENV = "env";
    private static final String PROD = "PROD";
    private static final String DEV = "DEV";

    private static final String URL_PROPERTY = "url";
    private static final String JDBC_DATABASE_URL = "JDBC_DATABASE_URL";
    private static final String JDBC_DATABASE_USERNAME = "JDBC_DATABASE_USERNAME";
    private static final String JDBC_DATABASE_PASSWORD = "JDBC_DATABASE_PASSWORD";
    private static final String SSLFACTORY = "sslfactory";
    private static final String SSL_FACTORY_VALUE = "org.postgresql.ssl.NonValidatingFactory";
    private static final String SSL = "ssl";

    private static final String DBCONFIG_DIR = "/dbconfig/";
    private static final String DEV_APPLICATION_PROPERTY_FILE = "dev.application.properties";

    //TODO:better to set env vars for DEV or load them as properties?
    public BasicDataSource getDataSource() {
        Properties properties = new Properties();
        String env = System.getProperty(ENV);
        BasicDataSource dataSource = new BasicDataSource();
        if (env == null || env.equalsIgnoreCase(DEV)) {
            try (InputStream resourceAsStream = getClass().getResourceAsStream(DBCONFIG_DIR + (
                    DEV_APPLICATION_PROPERTY_FILE))) {
                properties.load(resourceAsStream);
                dataSource.setUrl(properties.getProperty(URL_PROPERTY));
            } catch (IOException e) {
                throw new RuntimeException("Error reading properties ", e);
            }
        } else if (env.equalsIgnoreCase(PROD)) {
            System.out.println(env);
            dataSource.setUrl(System.getenv().get(JDBC_DATABASE_URL));
            dataSource.setUsername(System.getenv().get(JDBC_DATABASE_USERNAME));
            dataSource.setPassword(System.getenv().get(JDBC_DATABASE_PASSWORD));
            dataSource.addConnectionProperty(SSLFACTORY, SSL_FACTORY_VALUE);
            dataSource.addConnectionProperty(SSL, String.valueOf(true));
        }
        return dataSource;
    }
}
