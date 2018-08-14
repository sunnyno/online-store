package com.dzytsiuk.onlinestore.dao.jdbc

import com.dzytsiuk.onlinestore.dao.UserDao
import com.dzytsiuk.onlinestore.dao.jdbc.datasource.DBInitializer
import com.dzytsiuk.onlinestore.entity.User
import org.dbunit.dataset.IDataSet
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

class JdbcUserDaoITest {
    static final String PROPERTY_FILE_PATH = "/property/test.application.properties"
    static final DBInitializer DB_INITIALIZER = new DBInitializer()
    static final String SCHEMA_FILE_PATH = "/db/schema.sql"
    static final String DATASET_FILE_PATH = "/db/dataset/user-dataset.xml"

    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("context.xml")

    @BeforeClass
    static void setUp() {
        System.setProperty("spring.profiles.active", "test")
        System.setProperty("properties.path", PROPERTY_FILE_PATH)
        DB_INITIALIZER.createSchema(PROPERTY_FILE_PATH, SCHEMA_FILE_PATH)
    }

    @Before
    void importDataSet() throws Exception {
        IDataSet dataSet = DB_INITIALIZER.readDataSet(DATASET_FILE_PATH)
        DB_INITIALIZER.cleanlyInsert(dataSet)
    }


    @Test
    void findByLoginTest() {
        def expectedUser = new User(login: 'zhenya', password: 1234, salt: "salt")
        def userDao = applicationContext.getBean(UserDao.class)
        def actualUser = userDao.findByLogin("zhenya") as Optional<User>
        assertTrue(actualUser.isPresent())
        assertEquals(expectedUser, actualUser.get())
    }
}
