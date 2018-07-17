package com.dzytsiuk.onlinestore.dao;

import com.dzytsiuk.onlinestore.entity.User;

import javax.sql.DataSource;

public interface UserDao {
    User findByLogin(String login);

    void setDataSource(DataSource dataSource);

}
