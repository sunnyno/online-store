package com.dzytsiuk.onlinestore.dao.jdbc;

import com.dzytsiuk.onlinestore.dao.UserDao;
import com.dzytsiuk.onlinestore.dao.jdbc.mapper.UserRowMapper;
import com.dzytsiuk.onlinestore.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class JdbcUserDao implements UserDao {
    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();
    private static final String FIND_BY_LOGIN_SQL = "select password, salt from \"user\" where login = ?";
    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    private DataSource dataSource;

    @Override
    public Optional<User> findByLogin(String login) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_LOGIN_SQL)) {
            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                logger.info("Executing {}", FIND_BY_LOGIN_SQL);
                //login is unique in DB
                if (resultSet.next()) {
                    return Optional.of(USER_ROW_MAPPER.mapRow(resultSet, login));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting user", e);
        }

    }

    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
