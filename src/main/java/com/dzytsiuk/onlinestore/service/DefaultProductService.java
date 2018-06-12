package com.dzytsiuk.onlinestore.service;


import com.dzytsiuk.onlinestore.dao.ProductDao;
import com.dzytsiuk.onlinestore.entity.Product;

import java.util.List;

public class DefaultProductService implements ProductService {

    private ProductDao productDao;

    @Override
    public List<Product> getAll() {
        return productDao.getAllProducts();
    }

    @Override
    public void insert(Product product) {
        productDao.insertProduct(product);
    }

    @Override
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
}
