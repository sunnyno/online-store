package com.dzytsiuk.onlinestore.dao.jdbc

import com.dzytsiuk.onlinestore.dao.UserDao
import com.dzytsiuk.onlinestore.dao.jdbc.datasource.DBInitializer
import com.dzytsiuk.onlinestore.entity.User
import org.dbunit.dataset.IDataSet
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ["classpath:context.xml"])
class JdbcUserDaoITest {
    static final String PROPERTY_FILE_PATH = "/properties/test.application.properties"
    static DBInitializer dbInitializer = new DBInitializer()
    static final String SCHEMA_FILE_PATH = "/db/schema.sql"
    static final String DATASET_FILE_PATH = "/db/dataset/user-dataset.xml"
    @Autowired
    UserDao userDao

    @BeforeClass
    static void setUp() {
        System.setProperty("properties.path", PROPERTY_FILE_PATH)
        dbInitializer.createSchema(PROPERTY_FILE_PATH, SCHEMA_FILE_PATH)
    }

    @Before
    void importDataSet() throws Exception {
        IDataSet dataSet = dbInitializer.readDataSet(DATASET_FILE_PATH)
        dbInitializer.cleanlyInsert(dataSet)
    }


    @Test
    void findByLoginTest() {
        def expectedUser = new User(login: 'zhenya', password: 1234, salt: "salt")
        def actualUser = userDao.findByLogin("zhenya") as Optional<User>
        assertTrue(actualUser.isPresent())
        assertEquals(expectedUser, actualUser.get())
    }
}
