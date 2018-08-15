package com.dzytsiuk.onlinestore.security;

import com.dzytsiuk.onlinestore.entity.Product;
import com.dzytsiuk.onlinestore.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public class Session {
    private final String token;
    private final User user;
    private final LocalDateTime expireDate;
    private final List<Product> products;


    public Session(String token, User user, LocalDateTime expireDate, List<Product> products) {
        this.token = token;
        this.user = user;
        this.expireDate = expireDate;
        this.products = products;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }
}
