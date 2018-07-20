CREATE DATABASE "online-store";

CREATE TABLE product (
  id            SERIAL PRIMARY KEY,
  creation_date TIMESTAMP,
  name          VARCHAR(50),
  price         REAL
);


CREATE TABLE "user" (
  id            SERIAL PRIMARY KEY,
  login         VARCHAR(50) UNIQUE,
  password      INTEGER,
  salt          VARCHAR(50)
);
