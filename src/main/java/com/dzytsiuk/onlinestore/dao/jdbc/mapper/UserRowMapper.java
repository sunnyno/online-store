package com.dzytsiuk.onlinestore.dao.jdbc.mapper;

import com.dzytsiuk.onlinestore.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper {

    private static final String PASSWORD_COLUMN_NAME = "password";
    private static final String SALT_COLUMN_NAME = "salt";
    private static final String LOGIN_COLUMN_NAME = "login";

    public User mapRow(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setLogin(resultSet.getString(LOGIN_COLUMN_NAME));
        int password = resultSet.getInt(PASSWORD_COLUMN_NAME);
        String salt = resultSet.getString(SALT_COLUMN_NAME);
        user.setPassword(password);
        user.setSalt(salt);
        return user;
    }
}
