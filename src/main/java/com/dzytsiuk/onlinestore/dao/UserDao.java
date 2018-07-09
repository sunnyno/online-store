package com.dzytsiuk.onlinestore.dao;

import com.dzytsiuk.onlinestore.entity.User;

public interface UserDao {
    User findByLogin(String login);
}
