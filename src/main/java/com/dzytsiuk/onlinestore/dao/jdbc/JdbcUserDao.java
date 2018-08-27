package com.dzytsiuk.onlinestore.dao.jdbc;

import com.dzytsiuk.onlinestore.dao.UserDao;
import com.dzytsiuk.onlinestore.dao.jdbc.mapper.UserRowMapper;
import com.dzytsiuk.onlinestore.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
public class JdbcUserDao implements UserDao {
    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();
    private static final String FIND_BY_LOGIN_SQL = "select login, password, salt from \"user\" where login = ?";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private JdbcTemplate jdbcTemplateObject;


    @Override
    public Optional<User> findByLogin(String login) {
        logger.info("Executing query {}", FIND_BY_LOGIN_SQL);
        User user = jdbcTemplateObject.queryForObject(FIND_BY_LOGIN_SQL, USER_ROW_MAPPER, login);
        logger.info("Found user {}", user);
        return Optional.of(user);
    }

    @Autowired
    @Override
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }
}
