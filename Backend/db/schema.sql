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

    -- RÀNG BUỘC MỚI: CHO PHÉP 4 PHÒNG CHIẾU CÙNG PHIM, CÙNG GIỜ
    CONSTRAINT UQ_Schedule 
        UNIQUE (room_id, film_id, show_time_id, schedule_date),

    -- KHÓA NGOẠI
    CONSTRAINT FK_Schedule_Film FOREIGN KEY (film_id) REFERENCES films(id),
    CONSTRAINT FK_Schedule_Room FOREIGN KEY (room_id) REFERENCES rooms(id),
    CONSTRAINT FK_Schedule_ShowTime FOREIGN KEY (show_time_id) REFERENCES show_times(id)
);
GO

CREATE TABLE products 
(
   id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
   name NVARCHAR(100) NOT NULL,
   description NVARCHAR(MAX) NOT NULL,
   poster NVARCHAR(255),
   is_deleted BIT DEFAULT 0
);
GO


CREATE TABLE price_rules (
    id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    film_id UNIQUEIDENTIFIER NOT NULL,
    room_id UNIQUEIDENTIFIER NOT NULL,
    show_time_id UNIQUEIDENTIFIER NOT NULL,
    day_type NVARCHAR(20) NOT NULL,
    base_price DECIMAL(10,2) NOT NULL,
    created_at DATETIME2 DEFAULT SYSDATETIME(),
    is_deleted BIT NOT NULL DEFAULT 0,
    CONSTRAINT FK_PriceRules_Film FOREIGN KEY (film_id) REFERENCES films(id),
    CONSTRAINT FK_PriceRules_Room FOREIGN KEY (room_id) REFERENCES rooms(id),
    CONSTRAINT FK_PriceRules_ShowTime FOREIGN KEY (show_time_id) REFERENCES show_times(id),
    CONSTRAINT CHK_DayType CHECK (day_type IN ('WEEKDAY', 'WEEKEND', 'HOLIDAY', 'SPECIAL'))
);
GO

SELECT * FROM user_roles
SELECT * FROM users
SELECT * FROM roles
SELECT * FROM user_profiles
SELECT * FROM categories
SELECT * FROM films
SELECT * FROM film_categories

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