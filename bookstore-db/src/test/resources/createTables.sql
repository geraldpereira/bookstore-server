CREATE TABLE book (
  id int PRIMARY KEY AUTO_INCREMENT NOT NULL,
  title varchar(45) NOT NULL,
  author varchar(45) NOT NULL,
  releaseDate TIMESTAMP
);