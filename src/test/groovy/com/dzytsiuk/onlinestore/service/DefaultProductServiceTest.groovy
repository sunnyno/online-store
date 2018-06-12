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

        ProductService productService = new DefaultProductService(productDao: rsProductDao);
        def actualProducts = productService.getAll().collect()
        actualProducts.each { assertTrue(expectedProducts.remove(it)) }
    }

//does it really test?
    @Test
    void insertTest() {
        def now = LocalDateTime.now()
        def productToInsert = new Product(id: 1, creationDate: now, name: 'test', price: 1000 as double)
        def expectedProducts = []
        def rsProductDao = [insertProduct: { product -> expectedProducts.add(productToInsert) }, getAllProducts: { getAllProducts -> expectedProducts }] as ProductDao

        ProductService productService = new DefaultProductService(productDao: rsProductDao);
        def actualProducts = productService.getAll().collect()
        actualProducts.each { assertTrue(expectedProducts.remove(it)) }


    }
}
