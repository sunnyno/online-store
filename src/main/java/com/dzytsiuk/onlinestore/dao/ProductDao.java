package com.dzytsiuk.onlinestore.dao;


import com.dzytsiuk.jdbcwrapper.JdbcTemplate;
import com.dzytsiuk.onlinestore.entity.Product;

import javax.sql.DataSource;
import java.util.List;

public interface ProductDao {

    List<Product> findAll();

    void save(Product product);

    void setJdbcTemplate(JdbcTemplate jdbcTemplate);

    Product findProductById(int productId);
}
