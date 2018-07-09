package com.dzytsiuk.onlinestore.service;

import com.dzytsiuk.onlinestore.dao.UserDao;
import com.dzytsiuk.onlinestore.entity.User;

public class DefaultUserService implements UserService {
    private UserDao userDao;

    @Override
    public User findByLogin(String login) {
        return userDao.findByLogin(login);
    }

    @Override
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
