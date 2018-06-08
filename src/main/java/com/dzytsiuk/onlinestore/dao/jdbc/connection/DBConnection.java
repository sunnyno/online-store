package com.dzytsiuk.onlinestore.dao.jdbc.connection;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    public static Connection connectToDB(String db) {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(new File("dbconfig","config.properties")));

            Connection connection = DriverManager.getConnection(properties.getProperty("url"),
                    properties.getProperty("login"),
                    properties.getProperty("pass"));
            connection.setCatalog(db);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to DB", e);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read DB Properties", e);
        }
    }
}
