package com.dzytsiuk.onlinestore.dao.jdbc

import com.dzytsiuk.onlinestore.entity.Product
import org.junit.Test

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import static org.junit.Assert.assertNotNull

class JdbcProductDaoITest {

    @Test
    void getAllProductsTest() {
        def time = "2018-06-12 17:52:23.023187"
        def now = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"));
        def product1 = new Product(creationDate: now, name: "test", price: 100.5 as double)
        def product2 = new Product(creationDate: now, name: "test1", price: 27.00 as double)
        def expectedProducts = [product1, product2]
        System.setProperty("properties", "prod.application.properties")

        JdbcProductDao jdbcProductDao = new JdbcProductDao(new DataSourceManager().getDataSource())
        jdbcProductDao.save(product1)
        jdbcProductDao.save(product2)
        def actualProducts = jdbcProductDao.findAll()
        expectedProducts.each {
            assertNotNull(actualProducts.find { it })
        }
        jdbcProductDao.delete(product1)
        jdbcProductDao.delete(product2)

    }

    @Test
    void insertProductTest() {
        def time = "2018-06-12 18:46:04.407570"
        def now = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"))
        def expectedProduct = new Product(id: -1, creationDate: now, name: "test", price: 100 as double)

        System.setProperty("properties", "prod.application.properties")
        JdbcProductDao jdbcProductDao = new JdbcProductDao(new DataSourceManager().getDataSource())
        jdbcProductDao.save(expectedProduct)

        def actualProducts = jdbcProductDao.findAll()
        assertNotNull(actualProducts.find { expectedProduct })
        jdbcProductDao.delete(expectedProduct)
    }
}
