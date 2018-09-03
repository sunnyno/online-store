package com.dzytsiuk.onlinestore.dao;


import com.dzytsiuk.onlinestore.entity.Product;

import javax.sql.DataSource;
import java.util.List;

public interface ProductDao {

    List<Product> findAll();

    void save(Product product);

    Product findProductById(int productId);

    void setDataSource(DataSource dataSource);
}