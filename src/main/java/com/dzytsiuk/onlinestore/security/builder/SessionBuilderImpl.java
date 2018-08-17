package com.dzytsiuk.onlinestore.security.builder;

import com.dzytsiuk.onlinestore.entity.CartItem;
import com.dzytsiuk.onlinestore.entity.Product;
import com.dzytsiuk.onlinestore.entity.User;
import com.dzytsiuk.onlinestore.security.Session;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class SessionBuilderImpl implements SessionBuilder {
    private String token;
    private User user;
    private LocalDateTime expireDate;
    private List<CartItem> cartItems;

    @Override
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }

    @Override
    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    @Override
    public Session getSession() {
        return new Session(token, user, expireDate, cartItems);
    }
}
