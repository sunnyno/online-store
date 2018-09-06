package com.dzytsiuk.onlinestore.entity;


import java.time.LocalDateTime;
import java.util.Objects;

public class Product {
    private int id;
    private LocalDateTime creationDate;
    private String name;
    private double price;

    public Product() {
    }

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id &&
                Double.compare(product.price, price) == 0 &&
                Objects.equals(creationDate, product.creationDate) &&
                Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, creationDate, name, price);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
