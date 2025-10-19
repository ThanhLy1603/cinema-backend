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


CREATE TABLE roles
(
    id INT IDENTITY(1,1) PRIMARY KEY,
    name VARCHAR(20) UNIQUE NOT NULL
);
DROP TABLE roles
GO

CREATE TABLE users
(
    id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    enabled BIT DEFAULT 1
);
DROP TABLE users
GO

CREATE TABLE user_roles
(
    user_id UNIQUEIDENTIFIER NOT NULL,
    role_id INT NOT NULL,
    PRIMARY key (role_id, user_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);
DROP TABLE user_roles
GO

SELECT * FROM user_roles
SELECT * FROM users
SELECT * FROM roles

SELECT name
FROM sys.default_constraints
WHERE parent_object_id = OBJECT_ID('users');


ALTER TABLE users
DROP CONSTRAINT DF__users__id__440B1D61;

ALTER TABLE users
DROP CONSTRAINT DF__users__enabled__44FF419A;


SELECT * FROM users