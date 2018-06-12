package com.dzytsiuk.onlinestore.dao.jdbc

import com.dzytsiuk.onlinestore.entity.Product
import org.junit.Test

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertTrue;

class JdbcProductDaoITest {

    @Test
    void getAllProductsTest() {
        def time = "2018-06-12 18:46:04.407570"
        def now = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"));
        def product1 = new Product(id: 1, creationDate: now, name: "cake", price: 100.5 as double)
        def product2 = new Product(id: 2, creationDate: now, name: "cookie", price: 27.00 as double)
        def expectedProducts = [product1, product2]
        System.setProperty("properties", "dev.application.properties")
        JdbcProductDao jdbcProductDao = new JdbcProductDao(new DataSourceManager().getPgSimpleDataSource())

        def actualProducts = jdbcProductDao.getAllProducts()
        expectedProducts.each { assertTrue(actualProducts.remove(it)) }


    }

    @Test
    void insertProductTest() {
        def time = "2018-06-12 18:46:04.407570"
        def now = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"));
        def expectedProduct = new Product(creationDate: now, name: "test", price: 100 as double)

        System.setProperty("properties", "dev.application.properties")
        JdbcProductDao jdbcProductDao = new JdbcProductDao(new DataSourceManager().getPgSimpleDataSource())
        jdbcProductDao.insertProduct(expectedProduct)

        def actualProducts = jdbcProductDao.getAllProducts()
        assertNotNull(actualProducts.find {expectedProduct})
    }
}
