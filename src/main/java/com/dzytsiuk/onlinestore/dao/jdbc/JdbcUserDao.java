package com.dzytsiuk.onlinestore.dao.jdbc;

import com.dzytsiuk.onlinestore.dao.UserDao;
import com.dzytsiuk.onlinestore.dao.jdbc.mapper.UserRowMapper;
import com.dzytsiuk.onlinestore.entity.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUserDao implements UserDao {
    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();
    private DataSource dataSource;

    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User findByLogin(String login) {
        String query = "select password from \"user\" where login = \'" + login + "\';";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            System.out.println("Executing " + query);
            return USER_ROW_MAPPER.mapRow(resultSet, login);
        } catch (SQLException e) {
            throw new RuntimeException("Error getting products", e);
        }

    }

}
