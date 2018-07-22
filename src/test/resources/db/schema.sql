
CREATE TABLE IF NOT EXISTS PRODUCT (
  id            INTEGER PRIMARY KEY AUTO_INCREMENT,
  creation_date TIMESTAMP,
  name          VARCHAR(50),
  price         REAL
);


CREATE TABLE IF NOT EXISTS USER (
  id            INTEGER PRIMARY KEY AUTO_INCREMENT,
  login         VARCHAR(50) UNIQUE,
  password      INTEGER,
  salt          VARCHAR(50)
);
