package com.dzytsiuk.onlinestore.service;

import com.dzytsiuk.onlinestore.dao.ProductDao;
import com.dzytsiuk.onlinestore.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {
    List<Product> findAll();

    void save(Product product);

    void setProductDao(ProductDao productDao);
}
