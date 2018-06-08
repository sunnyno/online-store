package com.dzytsiuk.onlinestore.service;


import com.dzytsiuk.onlinestore.dao.ProductDao;
import com.dzytsiuk.onlinestore.entity.Product;

import java.util.List;

public class ProductService {

    private ProductDao productDao;

    public List<Product> getAll() {
        return productDao.getAllProducts();
    }

    public void insert(Product product) {
        productDao.insertProduct(product);
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
}
