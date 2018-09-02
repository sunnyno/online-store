package com.dzytsiuk.onlinestore.dao.jdbc.mapper;

import com.dzytsiuk.onlinestore.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setLogin(rs.getString("login"));
        int password = rs.getInt("password");
        String salt = rs.getString("salt");
        user.setPassword(password);
        user.setSalt(salt);
        return user;
    }
}
