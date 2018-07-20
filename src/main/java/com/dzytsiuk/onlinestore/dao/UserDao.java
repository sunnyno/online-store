package com.dzytsiuk.onlinestore.dao;

import com.dzytsiuk.onlinestore.entity.User;

import javax.sql.DataSource;
import java.util.Optional;

public interface UserDao {
    Optional<User> findByLogin(String login);

    void setDataSource(DataSource dataSource);

}
