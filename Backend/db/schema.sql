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


SELECT * FROM user_roles
SELECT * FROM users
SELECT * FROM roles
SELECT * FROM user_profiles

SELECT name
FROM sys.default_constraints
WHERE parent_object_id = OBJECT_ID('users');

ALTER TABLE users
DROP CONSTRAINT DF__users__id__440B1D61;

ALTER TABLE users
DROP CONSTRAINT DF__users__enabled__44FF419A;

EXEC sp_help 'user_roles';


SELECT * FROM users