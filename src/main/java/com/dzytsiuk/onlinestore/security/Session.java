package com.dzytsiuk.onlinestore.security;

import com.dzytsiuk.onlinestore.entity.User;

import java.time.LocalDateTime;

public class Session {
    private String token;
    private User user;
    private LocalDateTime expireDate;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(int expireSeconds) {
        this.expireDate = LocalDateTime.now().plusSeconds(expireSeconds);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
