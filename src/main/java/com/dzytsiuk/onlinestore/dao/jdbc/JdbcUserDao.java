package com.dzytsiuk.onlinestore.dao.jdbc;

import com.dzytsiuk.jdbcwrapper.JdbcTemplate;
import com.dzytsiuk.jdbcwrapper.JdbcTemplateImpl;
import com.dzytsiuk.jdbcwrapper.exception.MoreThanOneObjectFoundException;
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
    private static final String FIND_BY_LOGIN_SQL = "select login, password, salt from \"user\" where login = ?";
    private static final Logger logger = LoggerFactory.getLogger(JdbcUserDao.class);
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<User> findByLogin(String login) {
        logger.info("Executing {}", FIND_BY_LOGIN_SQL);
        User user = jdbcTemplate.queryForObject(FIND_BY_LOGIN_SQL, USER_ROW_MAPPER, login);
        return Optional.ofNullable(user);
    }

    @Override
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
