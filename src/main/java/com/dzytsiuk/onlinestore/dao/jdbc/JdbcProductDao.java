package com.dzytsiuk.onlinestore.dao.jdbc;


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
    private static final Logger logger = LoggerFactory.getLogger(ProductDao.class);
    private DataSource dataSource;

    @Override
    public List<Product> findAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            logger.info("Executing {}", FIND_ALL_SQL);
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                products.add(PRODUCT_ROW_MAPPER.mapRow(resultSet));
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException("Error getting products", e);
        }
    }

    @Override
    public void save(Product product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL)) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(product.getCreationDate()));
            preparedStatement.setString(2, product.getName());
            preparedStatement.setDouble(3, product.getPrice());
            logger.info("Executing {}", SAVE_SQL);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting product " + product.getName(), e);
        }

    }


    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
