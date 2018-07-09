package com.dzytsiuk.onlinestore.service;

import com.dzytsiuk.onlinestore.dao.UserDao;
import com.dzytsiuk.onlinestore.entity.User;

public interface UserService {
    User findByLogin(String login);

    void setUserDao(UserDao userDao);
}
