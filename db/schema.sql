CREATE DATABASE online_store;

USE online_store;

CREATE TABLE product (
  id            BIGINT PRIMARY KEY AUTO_INCREMENT,
  creation_date TIMESTAMP,
  name          VARCHAR(50),
  price         DOUBLE
);