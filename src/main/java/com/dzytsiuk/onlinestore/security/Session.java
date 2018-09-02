package com.dzytsiuk.onlinestore.security;

import com.dzytsiuk.onlinestore.entity.User;

import java.time.LocalDateTime;

public class Session {
    private final String token;
    private final User user;
    private final LocalDateTime expireDate;


    public Session(String token, User user, LocalDateTime expireDate) {
        this.token = token;
        this.user = user;
        this.expireDate = expireDate;
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


}