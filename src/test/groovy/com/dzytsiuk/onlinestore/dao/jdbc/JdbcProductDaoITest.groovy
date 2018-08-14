package com.dzytsiuk.onlinestore.dao.jdbc

import com.dzytsiuk.onlinestore.dao.ProductDao
import com.dzytsiuk.onlinestore.dao.jdbc.datasource.DBInitializer
import com.dzytsiuk.onlinestore.entity.Product
import org.dbunit.dataset.IDataSet
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import static org.junit.Assert.assertNotNull

class JdbcProductDaoITest {

    static final String PROPERTY_FILE_PATH = "/property/test.application.properties"
    static final DBInitializer DB_INITIALIZER = new DBInitializer()
    static final String SCHEMA_FILE_PATH = "/db/schema.sql"
    static final String DATASET_FILE_PATH = "/db/dataset/product-dataset.xml"

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
    void getAllProductsTest() {
        def time = "2018-06-12 17:52:23.023187"
        def now = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"));
        def product1 = new Product(creationDate: now, name: "cake", price: 100.5 as double)
        def product2 = new Product(creationDate: now, name: "cake2", price: 300.00 as double)
        def expectedProducts = [product1, product2]

        def productDao = applicationContext.getBean(ProductDao.class)
        def actualProducts = productDao.findAll()
        expectedProducts.each {
            assertNotNull(actualProducts.find { it })
        }
    }

    @Test
    void insertProductTest() {
        def time = "2018-06-12 18:46:04.407570"
        def now = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"))
        def expectedProduct = new Product(creationDate: now, name: "test", price: 100 as double)
        def productDao = applicationContext.getBean(ProductDao.class)
        productDao.save(expectedProduct)
        def actualProducts = productDao.findAll()
        assertNotNull(actualProducts.find { expectedProduct })
    }
}
