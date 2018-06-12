package com.dzytsiuk.onlinestore.dao.jdbc;


import com.dzytsiuk.onlinestore.dao.ProductDao;
import com.dzytsiuk.onlinestore.entity.Product;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductDao implements ProductDao {

    private static final ProductRowMapper PRODUCT_ROW_MAPPER = new ProductRowMapper();

    private DataSource dataSource;
    public JdbcProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> getAllProducts() {
        String query = "select id, creation_date, name, price from product;";
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(query);) {
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Executing " + query);
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
    public void insertProduct(Product product) {
        String query = "insert into product(creation_date, name, price) values ('" + product.getCreationDate()
                + "', '" + product.getName() + "', " + product.getPrice() + ");";
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(query);) {
            System.out.println("Executing " + query);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting product " + product.getName(), e);
        }

    }
}
