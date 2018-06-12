package com.dzytsiuk.onlinestore.dao.jdbc

import com.dzytsiuk.onlinestore.entity.Product
import org.junit.Test

import java.sql.ResultSet
import java.sql.Timestamp
import java.time.LocalDateTime
import static org.junit.Assert.assertEquals;

class ProductRowMapperTest {

    @Test
    void mapRowTest() {
        LocalDateTime localDateTime = LocalDateTime.now()
        def rsMock = [getInt      : { id -> 1 },
                      getString   : { name -> "cake" },
                      getTimestamp: { creation_date -> Timestamp.valueOf(localDateTime) },
                      getDouble   : { price -> 15.56 as double }] as ResultSet
        Product expectedProduct = new Product(localDateTime, "cake", 15.56 as double);
        expectedProduct.setId(1);
        assertEquals(expectedProduct, new ProductRowMapper().mapRow(rsMock))
    }
}
