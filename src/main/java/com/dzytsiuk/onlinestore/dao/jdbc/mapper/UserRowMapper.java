package com.dzytsiuk.onlinestore.dao.jdbc.mapper;

import com.dzytsiuk.onlinestore.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper {

    private static final String PASSWORD_COLUMN_NAME = "password";
    private static final String SALT_COLUMN_NAME = "salt";

    public User mapRow(ResultSet resultSet, String login) throws SQLException {
        User user = new User();
        user.setLogin(login);
        int password = resultSet.getInt(PASSWORD_COLUMN_NAME);
        String salt = resultSet.getString(SALT_COLUMN_NAME);
        user.setPassword(password);
        user.setSalt(salt);
        return user;
    }
}
