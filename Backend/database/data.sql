USE CinemaSystem;

-- Nhập liệu cho bản roles

INSERT INTO roles VALUES
('ADMIN'),
('STAFF'),
('CUSTOMER');
GO


INSERT INTO users(username, password, email, enabled) VALUES
-- Ly
('LyAdmin', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'lynguyenthanh1603@gmail.com', 1),
('LyStaff', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'lynguyenthanh1603@gmail.com', 1),
('LyCustomer', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'lynguyenthanh1603@gmail.com', 1),

-- Thang
('ThangAdmin', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'thang@example.com', 1),
('ThangStaff', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'thang@example.com', 1),
('ThangCustomer', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'thang@example.com', 1),

-- Thien
('ThienAdmin', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'thien@example.com', 1),
('ThienStaff', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'thien@example.com', 1),
('ThienCustomer', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'thien@example.com', 1),

-- Quan
('QuanAdmin', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'quan@example.com', 1),
('QuanStaff', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'quan@example.com', 1),
('QuanCustomer', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'quan@example.com', 1),

-- Kiet
('KietAdmin', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'kiet@example.com', 1),
('KietStaff', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'kiet@example.com', 1),
('KietCustomer', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'kiet@example.com', 1),

-- My
('MyAdmin', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'my@example.com', 1),
('MyStaff', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'my@example.com', 1),
('MyCustomer', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'my@example.com', 1);
GO

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