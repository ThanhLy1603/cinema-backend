USE CinemaSystem;

-- Nhập liệu cho bản roles

INSERT INTO roles VALUES
('ADMIN'),
('STAFF'),
('CUSTOMER');
GO

-- Nhập liệu cho bảng users

INSERT INTO users(username, password, email, enabled) VALUES
-- Ly
('LyAdmin', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'lyadmin@gmail.com', 1),
('LyStaff', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'lystaff@gmail.com', 1),
('LyCustomer', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'lycustomer@gmail.com', 1),

-- Thang
('ThangAdmin', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'thangadmin@gmail.com', 1),
('ThangStaff', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'thangstaff@gmail.com', 1),
('ThangCustomer', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'thangcustomer@gmail.com', 1),

-- Thien
('ThienAdmin', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'thienadmin@gmail.com', 1),
('ThienStaff', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'thienstaff@gmail.com', 1),
('ThienCustomer', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'thiencustomer@gmail.com', 1),

-- Quan
('QuanAdmin', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'quan@example.com', 1),
('QuanStaff', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'quan@example.com', 1),
('QuanCustomer', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'quan@example.com', 1),

-- Kiet
('KietAdmin', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'kietadmin@gmail.com', 1),
('KietStaff', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'kietstaff@gmail.com', 1),
('KietCustomer', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'kietcustomer@gmail.com', 1),

-- My
('MyAdmin', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'myadmin@admin.com', 1),
('MyStaff', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'mystaff@gmail.com', 1),
('MyCustomer', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'mycustomer@gmail.com', 1);
GO

-- Nhập liệu cho bảng user_roles

-- ADMIN
INSERT INTO user_roles (user_id, role_id)
SELECT id, 1 FROM users WHERE username LIKE '%Admin';
GO

-- STAFF
INSERT INTO user_roles (user_id, role_id)
SELECT id, 2 FROM users WHERE username LIKE '%Staff';
GO

-- CUSTOMER
INSERT INTO user_roles (user_id, role_id)
SELECT id, 3 FROM users WHERE username LIKE '%Customer';
GO


SELECT * FROM roles
SELECT * FROM users
SELECT * FROM user_roles

SELECT username, email, password FROM users WHERE email = 'lycustomer@gmail.com';