package com.dzytsiuk.onlinestore.dao.jdbc.mapper;


import com.dzytsiuk.onlinestore.entity.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet resultSet, int i) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt("id"));
        Timestamp date = resultSet.getTimestamp("creation_date");
        product.setCreationDate(date.toLocalDateTime());
        product.setName(resultSet.getString("name"));
        product.setPrice(resultSet.getDouble("price"));
        return product;
    }
}
