package com.dzytsiuk.onlinestore.service;


import com.dzytsiuk.onlinestore.dao.ProductDao;
import com.dzytsiuk.onlinestore.entity.Product;

import java.util.List;

public class DefaultProductService implements ProductService {

    private ProductDao productDao;

    @Override
    public List<Product> getAll() {
        return productDao.findAll();
    }

    @Override
    public void insert(Product product) {
        productDao.save(product);
    }

    @Override
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
}
