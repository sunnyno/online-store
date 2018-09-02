package com.dzytsiuk.onlinestore.security.builder;


import com.dzytsiuk.onlinestore.entity.User;
import com.dzytsiuk.onlinestore.security.Session;

import java.time.LocalDateTime;

public interface SessionBuilder {

    void setToken(String token);

    void setUser(User user);

    void setExpireDate(LocalDateTime expireDate);

    Session getSession();
}