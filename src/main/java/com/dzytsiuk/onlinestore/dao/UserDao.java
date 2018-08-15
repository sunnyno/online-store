package com.dzytsiuk.onlinestore.dao;

import com.dzytsiuk.jdbcwrapper.JdbcTemplate;
import com.dzytsiuk.onlinestore.entity.User;

import java.util.Optional;

public interface UserDao {
    Optional<User> findByLogin(String login);

    void setJdbcTemplate(JdbcTemplate jdbcTemplate);

}
