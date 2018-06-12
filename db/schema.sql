CREATE DATABASE "online-store";

CREATE TABLE product (
  id            SERIAL PRIMARY KEY,
  creation_date TIMESTAMP,
  name          VARCHAR(50),
  price         REAL
);