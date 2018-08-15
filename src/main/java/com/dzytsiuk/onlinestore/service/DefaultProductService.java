package com.dzytsiuk.onlinestore.service;


import com.dzytsiuk.onlinestore.dao.ProductDao;
import com.dzytsiuk.onlinestore.entity.Product;

import java.util.List;

public class DefaultProductService implements ProductService {

    private ProductDao productDao;

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public void save(Product product) {
        productDao.save(product);
    }

    @Override
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public Product findProductById(int productId) {
        return productDao.findProductById(productId);
    }
}
