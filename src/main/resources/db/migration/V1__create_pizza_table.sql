CREATE TABLE Pizza (
                       id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       description VARCHAR(1000) NOT NULL,
                       price DECIMAL(10,2) NOT NULL,
                       base_price DECIMAL(10,2) NOT NULL ,
                       image_url VARCHAR(1000)
);
