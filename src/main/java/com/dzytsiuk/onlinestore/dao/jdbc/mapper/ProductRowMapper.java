package com.dzytsiuk.onlinestore.dao.jdbc.mapper;


import com.dzytsiuk.jdbcwrapper.mapper.RowMapper;
import com.dzytsiuk.onlinestore.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ProductRowMapper implements RowMapper<Product> {
    public Product mapRow(ResultSet resultSet) throws SQLException {
            Product product = new Product();
            product.setId(resultSet.getInt("id"));
            Timestamp date = resultSet.getTimestamp("creation_date");
            product.setCreationDate(date.toLocalDateTime());
            product.setName(resultSet.getString("name"));
            product.setPrice(resultSet.getDouble("price"));
            return product;
    }
}
