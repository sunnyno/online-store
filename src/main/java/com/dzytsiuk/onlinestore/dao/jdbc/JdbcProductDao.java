package com.dzytsiuk.onlinestore.dao.jdbc;


import com.dzytsiuk.jdbcwrapper.JdbcTemplate;
import com.dzytsiuk.jdbcwrapper.JdbcTemplateImpl;
import com.dzytsiuk.onlinestore.dao.ProductDao;
import com.dzytsiuk.onlinestore.dao.jdbc.mapper.ProductRowMapper;
import com.dzytsiuk.onlinestore.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductDao implements ProductDao {
    private static final ProductRowMapper PRODUCT_ROW_MAPPER = new ProductRowMapper();
    private static final String FIND_ALL_SQL = "select id, creation_date, name, price from product;";
    private static final String SAVE_SQL = "insert into product(creation_date, name, price) values (?,?,?);";
    private static final Logger logger = LoggerFactory.getLogger(JdbcProductDao.class);
    private static final String FIND_BY_ID_SQL = "select id, creation_date, name, price from product where id = ?;";
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Product> findAll() {

        logger.info("Executing {}", FIND_ALL_SQL);
        return jdbcTemplate.query(FIND_ALL_SQL, PRODUCT_ROW_MAPPER);
    }

    @Override
    public void save(Product product) {
        logger.info("Executing {}", SAVE_SQL);
        jdbcTemplate.update(SAVE_SQL, Timestamp.valueOf(product.getCreationDate()), product.getName(), product.getPrice());
    }

    @Override
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Product findProductById(int productId) {
        logger.info("Executing {}", FIND_BY_ID_SQL);
        return jdbcTemplate.queryForObject(FIND_BY_ID_SQL, PRODUCT_ROW_MAPPER, productId);
    }
}
