package com.dzytsiuk.onlinestore.security.builder;

import com.dzytsiuk.onlinestore.entity.Product;
import com.dzytsiuk.onlinestore.entity.User;
import com.dzytsiuk.onlinestore.security.Session;

import java.time.LocalDateTime;
import java.util.List;

public interface SessionBuilder {

    void setToken(String token);

    void setUser(User user);

    void setExpireDate(LocalDateTime expireDate);

    void setProducts(List<Product> products);

    Session getSession();
}
