package com.dzytsiuk.onlinestore.service

import com.dzytsiuk.onlinestore.dao.ProductDao
import com.dzytsiuk.onlinestore.entity.Product
import org.junit.Test

import java.time.LocalDateTime

import static org.junit.Assert.assertTrue

class DefaultProductServiceTest {
    @Test
    void getAllTest() {
        def now = LocalDateTime.now()
        def expectedProducts = [new Product(id: 1, creationDate: now, name: 'test', price: 1000 as double)]
        def rsProductDao = { getAllProducts -> expectedProducts } as ProductDao

        ProductService productService = new DefaultProductService(rsProductDao);
        def actualProducts = productService.findAll().collect()
        actualProducts.each { assertTrue(expectedProducts.remove(it)) }
    }

    @Test
    void insertTest() {
        def now = LocalDateTime.now()
        def productToInsert = new Product(id: 1, creationDate: now, name: 'test', price: 1000 as double)
        def expectedProducts = []
        def rsProductDao = [save: { product -> expectedProducts.add(productToInsert) }, findAll: { getAllProducts -> expectedProducts }] as ProductDao

        ProductService productService = new DefaultProductService(rsProductDao);
        def actualProducts = productService.findAll().collect()
        actualProducts.each { assertTrue(expectedProducts.remove(it)) }
    }
}
