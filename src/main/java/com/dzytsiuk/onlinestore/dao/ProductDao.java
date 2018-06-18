package com.dzytsiuk.onlinestore.dao;


import com.dzytsiuk.onlinestore.entity.Product;

import java.util.List;

public interface ProductDao {

    List<Product> findAll();

    void save(Product product);


}
