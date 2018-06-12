package com.dzytsiuk.onlinestore.service;

import com.dzytsiuk.onlinestore.dao.ProductDao;
import com.dzytsiuk.onlinestore.entity.Product;

import java.util.List;


public interface ProductService {
    List<Product> getAll();

    void insert(Product product);

    void setProductDao(ProductDao productDao);
}