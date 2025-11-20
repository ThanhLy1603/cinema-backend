CREATE LOGIN datn WITH PASSWORD = 'Datn@12345';
GO

CREATE DATABASE CinemaSystem;
GO

USE CinemaSystem;
GO

CREATE USER datn FOR LOGIN datn;
GO

ALTER ROLE db_owner ADD MEMBER datn;
GO

-- Bảng roles
CREATE TABLE roles (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name VARCHAR(20) UNIQUE NOT NULL
);
GO

-- Bảng users (sử dụng username làm PK)
CREATE TABLE users (
    username VARCHAR(50) NOT NULL PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    enabled BIT DEFAULT 1
);
GO

-- Bảng user_roles (nhiều-nhiều giữa users và roles)
CREATE TABLE user_roles (
    username VARCHAR(50) NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (role_id, username),
    FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);
GO

-- Bảng user_profiles (1-1 với users)
CREATE TABLE user_profiles (
    username VARCHAR(50) PRIMARY KEY,  -- vừa là PK vừa là FK
    full_name NVARCHAR(100),
    gender BIT,
    phone VARCHAR(20),
    address NVARCHAR(255),
    birthday DATE,
    avatar_url NVARCHAR(255),
    FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE
);
GO

CREATE TABLE categories
(
    id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    name NVARCHAR(255) NOT NULL,
    is_deleted BIT NOT NULL DEFAULT 0
);
GO

CREATE TABLE films (
    id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    name NVARCHAR(255) NOT NULL,
    country NVARCHAR(100),
    director NVARCHAR(255),
    actor NVARCHAR(MAX), 
    description NVARCHAR(MAX),
    duration INT,
    poster NVARCHAR(255),
    trailer NVARCHAR(255),
    release_date DATE,
    
    status NVARCHAR(20) NOT NULL DEFAULT 'active',
    CONSTRAINT CHK_FilmStatus CHECK (status IN ('active', 'inactive', 'upcoming')),
    
    is_deleted BIT NOT NULL DEFAULT 0
);
GO

CREATE TABLE film_categories (
    -- ID bản ghi trung gian có thể là INT hoặc UUID, dùng INT cho dễ đọc và index nhanh hơn nếu cần
    id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(), 
    
    -- Khóa ngoại phải là UNIQUEIDENTIFIER
    film_id UNIQUEIDENTIFIER NOT NULL,
    category_id UNIQUEIDENTIFIER NOT NULL,
    
    -- Khóa ngoại
    CONSTRAINT FK_FC_Film FOREIGN KEY (film_id) 
        REFERENCES films(id) 
        ON DELETE CASCADE,
        
    CONSTRAINT FK_FC_Category FOREIGN KEY (category_id) 
        REFERENCES categories(id) 
        ON DELETE CASCADE,
    
    -- Ràng buộc duy nhất
    CONSTRAINT UQ_FilmCategory UNIQUE (film_id, category_id) 
);
GO

CREATE TABLE seat_types (
    id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    name NVARCHAR(30) NOT NULL,
    is_deleted BIT NOT NULL DEFAULT 0
);
GO

CREATE TABLE rooms (
    id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    name NVARCHAR(50) NOT NULL,
    status NVARCHAR(20) NOT NULL DEFAULT 'active',
    is_deleted BIT NOT NULL DEFAULT 0,
    CONSTRAINT CHK_RoomStatus CHECK (status IN ('active', 'closed', 'maintenance'))
);
GO

CREATE TABLE show_times (
    id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    start_time TIME NOT NULL,
    is_deleted BIT NOT NULL DEFAULT 0
);
GO

CREATE TABLE seats (
    id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    room_id UNIQUEIDENTIFIER NOT NULL,
    seat_type_id UNIQUEIDENTIFIER NOT NULL,
    position NVARCHAR(10) NOT NULL,
    active BIT NOT NULL DEFAULT 1, 
    is_deleted BIT NOT NULL DEFAULT 0,
    CONSTRAINT FK_Seats_Room FOREIGN KEY (room_id) REFERENCES rooms(id),
    CONSTRAINT FK_Seats_SeatType FOREIGN KEY (seat_type_id) REFERENCES seat_types(id),
    CONSTRAINT UQ_SeatPositionInRoom UNIQUE (room_id, position)
);
GO

CREATE TABLE schedules (
    id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    film_id UNIQUEIDENTIFIER NOT NULL,
    room_id UNIQUEIDENTIFIER NOT NULL,
    show_time_id UNIQUEIDENTIFIER NOT NULL,
    schedule_date DATE NOT NULL,
    is_deleted BIT NOT NULL DEFAULT 0,

    -- RÀNG BUỘC THỰC TẾ: 1 phòng chỉ chiếu 1 phim tại 1 suất giờ / ngày
    CONSTRAINT UQ_Schedule UNIQUE (room_id, show_time_id, schedule_date),

    CONSTRAINT FK_Schedule_Film FOREIGN KEY (film_id) REFERENCES films(id),
    CONSTRAINT FK_Schedule_Room FOREIGN KEY (room_id) REFERENCES rooms(id),
    CONSTRAINT FK_Schedule_ShowTime FOREIGN KEY (show_time_id) REFERENCES show_times(id)
);
GO

CREATE TABLE products (
    id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    name NVARCHAR(100) NOT NULL,
    description NVARCHAR(500),
    poster NVARCHAR(255),
    is_deleted BIT NOT NULL DEFAULT 0
);
GO

CREATE TABLE product_prices (
    id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    product_id UNIQUEIDENTIFIER NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE,
    is_deleted BIT NOT NULL DEFAULT 0,
    CONSTRAINT FK_ProductPrice_Product FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    CONSTRAINT CHK_Price_Positive CHECK (price > 0)
);
GO

CREATE TABLE promotions (
    id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    name NVARCHAR(100) NOT NULL,
    description NVARCHAR(255) NULL,
    poster NVARCHAR(100) NULL,
    discount_percent DECIMAL(5,2) NULL CHECK (discount_percent BETWEEN 0 AND 100),
    discount_amount DECIMAL(12,2) NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    active BIT DEFAULT 1,
    is_deleted BIT DEFAULT 0
);
GO

CREATE TABLE promotion_items (
    id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    promotion_id UNIQUEIDENTIFIER NOT NULL,
    product_id UNIQUEIDENTIFIER NULL,
    film_id UNIQUEIDENTIFIER NULL,
    seat_type_id UNIQUEIDENTIFIER NULL,
    note NVARCHAR(255) NULL,
    CONSTRAINT FK_PromoItem_Promo FOREIGN KEY (promotion_id) REFERENCES promotions(id) ON DELETE CASCADE,
    CONSTRAINT FK_PromoItem_Product FOREIGN KEY (product_id) REFERENCES products(id),
    CONSTRAINT FK_PromoItem_Film FOREIGN KEY (film_id) REFERENCES films(id),
    CONSTRAINT FK_PromoItem_SeatType FOREIGN KEY (seat_type_id) REFERENCES seat_types(id)
);
GO

CREATE TABLE promotion_rules (
    id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    promotion_id UNIQUEIDENTIFIER NOT NULL,
    rule_type NVARCHAR(50) NOT NULL, -- 'PERCENT','AMOUNT','BUY_X_GET_Y','FIXED_COMBO'
    rule_value NVARCHAR(MAX) NULL,    -- JSON: {"buy":2,"get":1} hoặc {"items":["popcorn","soda","nuggets"],"price":79000}
    FOREIGN KEY (promotion_id) REFERENCES promotions(id) ON DELETE CASCADE
);
GO


-- cấu trúc bảng price_tickets
CREATE TABLE price_tickets (
    id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    film_id UNIQUEIDENTIFIER NOT NULL,
    seat_type_id UNIQUEIDENTIFIER NOT NULL,
    show_time_id UNIQUEIDENTIFIER NOT NULL,    -- Yếu tố Giờ Chiếu
    day_type NVARCHAR(20) NOT NULL,            -- Yếu tố Ngày (WEEKDAY/WEEKEND/HOLIDAY)
    price DECIMAL(12,2) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NULL, 
    is_deleted BIT DEFAULT 0,
    CONSTRAINT FK_TicketPrice_Film FOREIGN KEY (film_id) REFERENCES films(id),
    CONSTRAINT FK_TicketPrice_SeatType FOREIGN KEY (seat_type_id) REFERENCES seat_types(id),
    CONSTRAINT FK_TicketPrice_ShowTime FOREIGN KEY (show_time_id) REFERENCES show_times(id),
    CONSTRAINT CHK_DayType CHECK (day_type IN ('WEEKDAY', 'WEEKEND', 'HOLIDAY', 'SPECIAL'))
);
GO

CREATE TABLE invoices (
    id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),

    -- Người tạo hóa đơn (staff/admin)
    created_by VARCHAR(50) NULL,
    
    -- Khách hàng đăng nhập (trực tuyến)
    username VARCHAR(50) NULL,

    -- Khách vãng lai
    customer_name NVARCHAR(100) NULL,
    customer_phone VARCHAR(20) NULL,
    customer_address NVARCHAR(100) NULL,

    -- Tổng tiền
    total_amount DECIMAL(12,2) NOT NULL,
    discount_amount DECIMAL(12,2) DEFAULT 0,
    final_amount DECIMAL(12,2) NOT NULL,

    status NVARCHAR(20) NOT NULL DEFAULT 'PENDING',  
        -- PENDING / PAID / CANCELLED

    created_at DATETIME DEFAULT GETDATE(),
    is_deleted BIT DEFAULT 0,

    FOREIGN KEY (username) REFERENCES users(username),
    FOREIGN KEY (created_by) REFERENCES users(username)
);
GO


CREATE TABLE invoice_tickets (
    id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    invoice_id UNIQUEIDENTIFIER NOT NULL,
    schedule_id UNIQUEIDENTIFIER NOT NULL,
    seat_id UNIQUEIDENTIFIER NOT NULL,
    ticket_price_id UNIQUEIDENTIFIER NOT NULL,
    price DECIMAL(12,2) NOT NULL,
    promotion_id UNIQUEIDENTIFIER NULL,
    
    is_used BIT DEFAULT 0,
    used_at DATETIME NULL,

    FOREIGN KEY (invoice_id) REFERENCES invoices(id) ON DELETE CASCADE,
    FOREIGN KEY (schedule_id) REFERENCES schedules(id),
    FOREIGN KEY (seat_id) REFERENCES seats(id),
    FOREIGN KEY (ticket_price_id) REFERENCES price_tickets(id),
    FOREIGN KEY (promotion_id) REFERENCES promotions(id),

    -- Một vé của 1 schedule + 1 seat chỉ được bán 1 lần
    -- CONSTRAINT UQ_ScheduleSeat UNIQUE (schedule_id, seat_id)
);
GO


CREATE TABLE invoice_products (
    id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    invoice_id UNIQUEIDENTIFIER NOT NULL,
    product_id UNIQUEIDENTIFIER NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(12,2) NOT NULL,
    promotion_id UNIQUEIDENTIFIER NULL,

    FOREIGN KEY (invoice_id) REFERENCES invoices(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (promotion_id) REFERENCES promotions(id)
);
GO

CREATE TABLE invoice_qrcodes (
    id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    invoice_id UNIQUEIDENTIFIER NOT NULL,
    qr_code NVARCHAR(MAX) NOT NULL,
    qr_type NVARCHAR(20) NOT NULL DEFAULT 'COMBINED',  
        -- TICKET / PRODUCT / COMBINED

    is_used BIT DEFAULT 0,
    used_at DATETIME NULL,
    created_at DATETIME DEFAULT GETDATE(),

    FOREIGN KEY (invoice_id) REFERENCES invoices(id) ON DELETE CASCADE
);
GO


-- Xóa bảng theo đúng thứ tự ràng buộc (nếu tồn tại)
DROP TABLE IF EXISTS user_profiles;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;

DROP TABLE IF EXISTS film_categories;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS films;

DROP TABLE IF EXISTS schedules;
DROP TABLE IF EXISTS seats;
DROP TABLE IF EXISTS rooms;
DROP TABLE IF EXISTS seat_types;
DROP TABLE IF EXISTS show_times;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS product_prices;

DROP TABLE IF EXISTS promotions;
DROP TABLE IF EXISTS promotion_items;
DROP TABLE IF EXISTS promotion_rules;
DROP TABLE IF EXISTS price_tickets;

DROP TABLE IF EXISTS invoices;
DROP TABLE IF EXISTS invoice_products;
DROP TABLE IF EXISTS invoice_tickets;
DROP TABLE IF EXISTS invoice_qrcodes;

-- Truy vấn các bảng
SELECT * FROM user_roles
SELECT * FROM users
SELECT * FROM roles
SELECT * FROM user_profiles
SELECT * FROM categories
SELECT * FROM films
SELECT * FROM film_categories
SELECT * FROM seat_types
SELECT * FROM rooms
SELECT * FROM show_times
SELECT * FROM seats
SELECT * FROM schedules
SELECT * FROM schedule_seats
SELECT * FROM products
SELECT * FROM product_prices
SELECT * FROM promotions
SELECT * FROM promotion_items
SELECT * FROM promotion_rules
SELECT * FROM price_tickets

SELECT * FROM invoices
SELECT * FROM invoice_products
SELECT * FROM invoice_tickets
SELECT * FROM invoice_qrcodes
GO


SELECT * FROM user_roles
SELECT * FROM users
SELECT * FROM roles
SELECT * FROM user_profiles
SELECT * FROM categories
SELECT * FROM films
SELECT * FROM film_categories
SELECT * FROM seat_types
SELECT * FROM rooms
SELECT * FROM show_times
SELECT * FROM seats
SELECT * FROM schedules
SELECT * FROM products



UPDATE user_profiles SET avatar_url = 'avatar.jpg' WHERE username like 'LyStaff'

SELECT name
FROM sys.default_constraints
WHERE parent_object_id = OBJECT_ID('users');

ALTER TABLE users
DROP CONSTRAINT DF__users__id__440B1D61;

ALTER TABLE users
DROP CONSTRAINT DF__users__enabled__44FF419A;

EXEC sp_help 'user_roles';


SELECT * FROM users

SELECT * FROM Films WHERE name like N'Tôi Thấy Hoa Vàng Trên Cỏ Xanh'
SELECT * FROM film_categories WHERE film_id like 0x718ABFD14D68FC4F879282AF16F2E27A

SELECT 
    TABLE_NAME,
    COLUMN_NAME,
    DATA_TYPE,
    CHARACTER_MAXIMUM_LENGTH
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_NAME = 'films' AND COLUMN_NAME = 'id';

DELETE FROM film_categories WHERE film_id = '56eb669a-d02e-4210-8a73-2658578a1a55'
DELETE FROM films WHERE id = '56eb669a-d02e-4210-8a73-2658578a1a55'

UPDATE films SET is_deleted = 0 WHERE id ='46913442-cdc2-4136-9a81-33c08e5e1fb7'










-- CREATE TABLE schedules (
--     id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
--     film_id UNIQUEIDENTIFIER NOT NULL,
--     room_id UNIQUEIDENTIFIER NOT NULL,
--     show_time_id UNIQUEIDENTIFIER NOT NULL,
--     schedule_date DATE NOT NULL,
--     is_deleted BIT NOT NULL DEFAULT 0,

--     -- RÀNG BUỘC MỚI: CHO PHÉP 4 PHÒNG CHIẾU CÙNG PHIM, CÙNG GIỜ
--     CONSTRAINT UQ_Schedule 
--         UNIQUE (room_id, film_id, show_time_id, schedule_date),

--     -- KHÓA NGOẠI
--     CONSTRAINT FK_Schedule_Film FOREIGN KEY (film_id) REFERENCES films(id),
--     CONSTRAINT FK_Schedule_Room FOREIGN KEY (room_id) REFERENCES rooms(id),
--     CONSTRAINT FK_Schedule_ShowTime FOREIGN KEY (show_time_id) REFERENCES show_times(id)
-- );
-- GO


-- CREATE TABLE product_orders (
--     id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
--     product_id UNIQUEIDENTIFIER NOT NULL,
--     invoice_id UNIQUEIDENTIFIER NOT NULL,
--     quantity INT NOT NULL DEFAULT 1,
--     qr_code_url NVARCHAR(255),
--     status NVARCHAR(20) NOT NULL DEFAULT 'PENDING',
--     is_deleted BIT NOT NULL DEFAULT 0,
--     CONSTRAINT FK_PO_Product FOREIGN KEY (product_id) REFERENCES products(id),
--     CONSTRAINT FK_PO_Invoice FOREIGN KEY (invoice_id) REFERENCES invoices(id)
-- );
-- GO


-- CREATE TABLE invoices (
--     id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
--     created_by VARCHAR(50) NULL, -- nhân viên
--     username VARCHAR(50) NULL,   -- khách hàng
--     total_amount DECIMAL(12,2) NOT NULL,
--     discount_amount DECIMAL(12,2) DEFAULT 0,
--     final_amount DECIMAL(12,2) NOT NULL,
--     created_at DATETIME DEFAULT GETDATE(),
--     is_deleted BIT DEFAULT 0,
--     CONSTRAINT FK_Invoices_User FOREIGN KEY (username) REFERENCES users(username),
--     CONSTRAINT FK_Invoices_Staff FOREIGN KEY (created_by) REFERENCES users(username)
-- );
-- GO