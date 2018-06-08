package com.dzytsiuk.onlinestore.dao;


import com.dzytsiuk.onlinestore.entity.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getAllProducts();

    void insertProduct(Product product);


}
