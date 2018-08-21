package com.dzytsiuk.onlinestore.security.builder;

import com.dzytsiuk.onlinestore.entity.User;
import com.dzytsiuk.onlinestore.security.Session;

import java.time.LocalDateTime;

public class SessionBuilderImpl implements SessionBuilder {

    private String token;
    private User user;
    private LocalDateTime expireDate;


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
    public Session getSession() {
        return new Session(token, user, expireDate);
    }
}
