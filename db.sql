CREATE
DATABASE phone_store_management;

USE
phone_store_management;

-- 1. Bảng Admin
CREATE TABLE admin
(
    id       INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50)  NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- 2. Bảng Product (Sản phẩm)
CREATE TABLE product
(
    id    INT PRIMARY KEY AUTO_INCREMENT,
    name  VARCHAR(100)   NOT NULL,
    brand VARCHAR(50)    NOT NULL,
    price DECIMAL(12, 2) NOT NULL,
    stock INT            NOT NULL
);

-- 3. Bảng Customer (Khách hàng)
CREATE TABLE customer
(
    id      INT PRIMARY KEY AUTO_INCREMENT,
    name    VARCHAR(100) NOT NULL,
    phone   VARCHAR(20),
    email   VARCHAR(100) UNIQUE,
    address VARCHAR(255)
);

-- 4. Bảng Invoice (Hóa đơn)
CREATE TABLE invoice
(
    id           INT PRIMARY KEY AUTO_INCREMENT,
    customer_id  INT,
    created_at   DATETIME DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(12, 2) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES Customer (id)
);

-- 5. Bảng Invoice_Details (Chi tiết hóa đơn)
CREATE TABLE invoice_detail
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    invoice_id INT,
    product_id INT,
    quantity   INT            NOT NULL,
    unit_price DECIMAL(12, 2) NOT NULL,
    FOREIGN KEY (invoice_id) REFERENCES Invoice (id),
    FOREIGN KEY (product_id) REFERENCES Product (id)
);

-- Thêm thong tin admin
INSERT INTO admin (username, password)
VALUES ('admin', '123');


-- Procedure lấy thông tin từ bảng admin
DELIMITER
//

CREATE PROCEDURE GetAdminInfo(IN input_username VARCHAR (50))
BEGIN
SELECT id, username, password
FROM Admin
WHERE username = input_username;
END
//

DELIMITER ;

-- Procedure thêm value vào product
DELIMITER
//

CREATE PROCEDURE AddProduct(
    IN p_name VARCHAR (100),
    IN p_brand VARCHAR (50),
    IN p_price DECIMAL (12,2),
    IN p_stock INT
)
BEGIN
INSERT INTO Product(name, brand, price, stock)
VALUES (p_name, p_brand, p_price, p_stock);
END
//

DELIMITER ;

-- Procedure lấy danh sách sản phẩm
DELIMITER
//

CREATE PROCEDURE getAllProduct()
BEGIN
SELECT *
FROM Product;
END
//

DELIMITER ;

-- Procedure tìm kiếm sản phẩm theo brand
DELIMITER
//

CREATE PROCEDURE searchProductsByBrand(IN brand_input VARCHAR (100))
BEGIN
SELECT *
FROM product
WHERE brand LIKE brand_input;
END
//

DELIMITER ;

-- Procedure lấy spham theo id
DELIMITER
//

CREATE PROCEDURE getProductById(IN id_input int)
BEGIN
SELECT *
FROM product
WHERE id = id_input;
END
//

DELIMITER ;

-- Procedure cập nhật sản phẩm theo id
DELIMITER
//

CREATE PROCEDURE updateProductById(
    IN p_id INT,
    IN p_name VARCHAR (100),
    IN p_brand VARCHAR (100),
    IN p_price DOUBLE,
    IN p_stock INT
)
BEGIN
UPDATE Product
SET name  = p_name,
    brand = p_brand,
    price = p_price,
    stock = p_stock
WHERE id = p_id;
END
//

DELIMITER ;

-- Procedure xóa sản phẩm theo id
DELIMITER //

CREATE PROCEDURE deleteProductById(IN id_input int)
BEGIN
DELETE
FROM product
WHERE id = id_input;
END //

DELIMITER ;

-- Procedure tìm kiếm điện thoại theo khoảng giá
DELIMITER //

CREATE PROCEDURE searchProductByPrice(IN min_price int, IN max_price int)
BEGIN
SELECT *
FROM product
WHERE price >= min_price
  AND price <= max_price;
END //

DELIMITER ;

-- Procedure tìm kiếm sản phẩm theo tồn kho
DELIMITER //

CREATE PROCEDURE searchProductByStock(IN min_stock int, IN max_stock int)
BEGIN
SELECT *
FROM product
WHERE stock BETWEEN min_stock AND max_stock;
END //

DELIMITER ;
