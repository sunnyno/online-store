package com.dzytsiuk.onlinestore.dao.jdbc;


import com.dzytsiuk.onlinestore.entity.Product;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.TimeZone;

public class ProductRowMapper {
    public Product mapRow(ResultSet resultSet) {
        try {
            Product product = new Product();
            product.setId(resultSet.getInt("id"));
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(TimeZone.getDefault());
            Timestamp date = resultSet.getTimestamp("creation_date", calendar);
            LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()),
                    ZoneId.systemDefault());
            product.setCreationDate(localDateTime);
            product.setName(resultSet.getString("name"));
            product.setPrice(resultSet.getDouble("price"));
            return product;
        } catch (SQLException e) {
            throw new RuntimeException("Error mapping row ", e);
        }
    }
}
