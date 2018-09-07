package com.dzytsiuk.onlinestore.service;


import com.dzytsiuk.onlinestore.dao.ProductDao;
import com.dzytsiuk.onlinestore.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DefaultProductService implements ProductService {
    private final ProductDao productDao;

    @Autowired
    public DefaultProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public void save(Product product) {
        product.setCreationDate(LocalDateTime.now());
        productDao.save(product);
    }

    @Override
    public Product findProductById(int productId) {
        return productDao.findProductById(productId);
    }

}