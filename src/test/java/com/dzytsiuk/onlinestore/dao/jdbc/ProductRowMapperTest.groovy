package com.dzytsiuk.onlinestore.dao.jdbc

import com.dzytsiuk.onlinestore.entity.Product

import java.sql.ResultSet
import java.sql.Timestamp
import java.time.LocalDateTime

class ProductRowMapperTest extends GroovyTestCase {
    void testMapRow() {
        LocalDateTime localDateTime = LocalDateTime.now()
        def rsMock = [getInt      : { id -> 1 },
                      getString   : { name -> "cake" },
                      getTimestamp: { creation_date, calendar -> Timestamp.valueOf(localDateTime) },
                      getDouble   : { price -> 15.56 as double }] as ResultSet
        Product expectedProduct = new Product(localDateTime, "cake", 15.56 as double);
        expectedProduct.setId(1);
        assertEquals(expectedProduct, new ProductRowMapper().mapRow(rsMock))
    }
}
