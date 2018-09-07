package com.dzytsiuk.onlinestore.service;


import com.dzytsiuk.onlinestore.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();

    void save(Product product);

    Product findProductById(int productId);
}