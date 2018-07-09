package com.dzytsiuk.onlinestore.dao.jdbc.mapper;

import com.dzytsiuk.onlinestore.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper {

    private static final String PASSWORD_COLUMN = "password";

    public User mapRow(ResultSet resultSet, String login){
        User user = new User();
        try {
            if(resultSet.next()){
                user.setLogin(login);
                String password = resultSet.getString(PASSWORD_COLUMN);
                user.setPassword(password);
            }
            return user;
        } catch (SQLException e) {
            throw  new RuntimeException("Error mapping user row", e);
        }
    }
}
