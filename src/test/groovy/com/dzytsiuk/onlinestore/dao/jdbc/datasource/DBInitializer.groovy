package com.dzytsiuk.onlinestore.dao.jdbc.datasource

import org.dbunit.IDatabaseTester
import org.dbunit.JdbcDatabaseTester
import org.dbunit.database.DatabaseConfig
import org.dbunit.database.IDatabaseConnection
import org.dbunit.dataset.IDataSet
import org.dbunit.dataset.xml.FlatXmlDataSet
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder
import org.dbunit.operation.DatabaseOperation
import org.h2.Driver
import org.h2.tools.RunScript

import java.nio.charset.Charset

class DBInitializer {
    static final String JDBC_DRIVER = Driver.class.getName()
    def url
    def username
    def password

    void createSchema(String propertyFilePath, String schemaFilePath) {
        Properties properties = new Properties()
        properties.load(getClass().getResourceAsStream(propertyFilePath))
        url = properties.getProperty("url")
        username = properties.getProperty("username")
        password = properties.getProperty("password")
        RunScript.execute(url, username, password, getClass().getResource(schemaFilePath).getFile(), Charset.defaultCharset(), false)
    }


    IDataSet readDataSet(String datasetFilePath) throws Exception {
        return new FlatXmlDataSetBuilder().build(new File(getClass().getResource(datasetFilePath).getFile()))
    }

    void cleanlyInsert(IDataSet dataSet) throws Exception {
        IDatabaseTester databaseTester = new JdbcDatabaseTester(JDBC_DRIVER, url, username, password)
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT)
        databaseTester.setDataSet(dataSet)
        databaseTester.onSetup()
    }

}
