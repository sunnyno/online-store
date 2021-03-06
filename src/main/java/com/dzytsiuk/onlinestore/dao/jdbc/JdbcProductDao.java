package com.dzytsiuk.onlinestore.dao.jdbc;


import com.dzytsiuk.onlinestore.dao.ProductDao;
import com.dzytsiuk.onlinestore.dao.jdbc.mapper.ProductRowMapper;
import com.dzytsiuk.onlinestore.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class JdbcProductDao implements ProductDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final ProductRowMapper PRODUCT_ROW_MAPPER = new ProductRowMapper();
    private static final String FIND_ALL_SQL = "select id, creation_date, name, price from product;";
    private static final String SAVE_SQL = "insert into product(creation_date, name, price) values (?,?,?);";
    private static final String FIND_BY_ID_SQL = "select id, creation_date, name, price from product where id = ?;";

    private JdbcTemplate jdbcTemplateObject;

    @Override
    public List<Product> findAll() {
        logger.info("Executing query {}", FIND_ALL_SQL);
        return jdbcTemplateObject.query(FIND_ALL_SQL, PRODUCT_ROW_MAPPER);
    }

    @Override
    public void save(Product product) {
        logger.info("Executing query {}", SAVE_SQL);
        jdbcTemplateObject.update(SAVE_SQL, Timestamp.valueOf(product.getCreationDate()), product.getName(), product.getPrice());
    }

    @Override
    public Product findProductById(int productId) {
        logger.info("Executing {}", FIND_BY_ID_SQL);
        return jdbcTemplateObject.queryForObject(FIND_BY_ID_SQL, PRODUCT_ROW_MAPPER, productId);

    }

    @Autowired
    @Override
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }
}