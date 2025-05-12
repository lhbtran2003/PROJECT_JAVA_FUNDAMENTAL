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

CREATE PROCEDURE GetAdminInfo(IN input_username VARCHAR(50))
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
    IN p_name VARCHAR(100),
    IN p_brand VARCHAR(50),
    IN p_price DECIMAL(12, 2),
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

CREATE PROCEDURE searchProductsByBrand(IN brand_input VARCHAR(100))
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
    IN p_name VARCHAR(100),
    IN p_brand VARCHAR(100),
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

-- Procedure thêm value vào customer
DELIMITER
//

CREATE PROCEDURE AddCustomer(
    IN c_name VARCHAR(100),
    IN c_phone VARCHAR(20),
    IN c_email VARCHAR(100),
    IN c_address VARCHAR(255)
)
BEGIN
    INSERT INTO customer(name, phone, email, address)
    VALUES (c_name, c_phone, c_email, c_address);
END
//

DELIMITER ;

-- Procedure lấy danh sách khách hàng
DELIMITER //

CREATE PROCEDURE getAllCustomer()
BEGIN
    SELECT * FROM customer;
end //
DELIMITER ;

-- Procedure thêm mới khách hàng
DELIMITER //

CREATE PROCEDURE AddCustomer(
    IN c_name VARCHAR(100),
    IN c_phone VARCHAR(20),
    IN c_email VARCHAR(100),
    IN c_address VARCHAR(255)
)
BEGIN
    INSERT INTO customer(name, phone, email, address)
    VALUES (c_name, c_phone, c_email, c_address);
END //

DELIMITER ;

-- Procedure cập nhật thông tin khách hàng
DELIMITER //

CREATE PROCEDURE updateCustomerById(
    IN c_id INT,
    IN c_name VARCHAR(100),
    IN c_phone VARCHAR(20),
    IN c_email VARCHAR(100),
    IN c_address VARCHAR(255)
)
BEGIN
    UPDATE customer
    SET name    = c_name,
        phone   = c_phone,
        email   = c_email,
        address = c_address
    WHERE id = c_id;
END //

DELIMITER ;

-- Procedure xóa khách hàng theo id
DELIMITER //

CREATE PROCEDURE deleteCustomerById(IN id_input INT)
BEGIN
    DELETE
    FROM customer
    WHERE id = id_input;
end //

-- Procedure tìm kiếm khách hàng theo tên
DELIMITER //

CREATE PROCEDURE searchCustomerByName(IN keyword VARCHAR(100))
BEGIN
    SELECT *
    FROM Customer
    WHERE name LIKE CONCAT('%', keyword, '%');
END //

DELIMITER ;

-- Procedure tìm kiếm sản phẩm theo tên
DELIMITER //

CREATE PROCEDURE searchProductByName(IN keyword VARCHAR(100))
BEGIN
    SELECT *
    FROM Product
    WHERE name LIKE CONCAT('%', keyword, '%');
END //

DELIMITER ;

-- Procedure lấy danh sách hóa đơn (invoice)
DELIMITER //

CREATE PROCEDURE getAllInvoice()
BEGIN
    SELECT * FROM invoice;
END //

DELIMITER ;

-- Procedure tạo moi hóa đơn
DELIMITER //

CREATE PROCEDURE addInvoice(
    IN i_customer_id INT,
    IN i_total_amount DECIMAL(12, 2),
    OUT invoice_id INT
)
BEGIN
    INSERT INTO invoice(customer_id, total_amout)
    VALUES (i_customer_id, i_total_amount);

    SET invoice_id = LAST_INSERT_ID();
END //

DELIMITER ;


-- Procedure tạo chi tiết hóa đơn (invoice_detail)
DELIMITER //

CREATE PROCEDURE addInvoiceDetail(
    IN i_invoice_id INT,
    IN i_product_id INT,
    IN i_quantity INT,
    IN i_unit_price DECIMAL(12, 2)
)
BEGIN
    INSERT INTO invoice_detail(invoice_id, product_id, quantity, unit_price)
    VALUES (i_invoice_id, i_product_id, i_quantity, i_unit_price);
END //

DELIMITER ;

-- Procedure cập nhật so lượng tồn kho
DELIMITER //
CREATE PROCEDURE updateProductStock(
    IN p_id INT,
    IN p_quantity INT
)
BEGIN
    DECLARE current_stock INT;

    -- lấy so lượng tồn kho hiện tại
    SELECT stock
    INTO current_stock
    FROM product
    WHERE id = p_id;

    -- kiểm tra số lượng
    IF current_stock IS NULL THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Sản phẩm không tồn tại';
    ELSEIF current_stock < p_quantity THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Sản phẩm không đủ';
    ELSE
        -- tiến hành trừ số lượng tồn kho
        UPDATE product
        SET stock = stock - p_quantity
        WHERE id = p_id;
    END IF;

END //
DELIMITER ;

-- Procedure tìm kiếm hóa đơn theo tên khách hàng
DELIMITER //
CREATE PROCEDURE searchInvoiceByCustomerName(IN c_name VARCHAR(100))
BEGIN
    SELECT i.id, c.name, i.create_at, i.total_amout
    FROM invoice i
             INNER JOIN customer c
                        ON c.id = i.customer_id
    WHERE c.name LIKE CONCAT('%', c_name, '%');
END //
DELIMITER ;

-- Procedure tìm kiếm hóa đơn theo ngày tháng năm tạo
DELIMITER //
CREATE PROCEDURE searchInvoiceByDate(IN p_date DATE)
BEGIN
    SELECT i.id, c.name, i.create_at, i.total_amout
    FROM invoice i
             INNER JOIN customer c
                        ON c.id = i.customer_id
    WHERE DATE(i.create_at) = p_date;
END //
DELIMITER ;




