USE CinemaSystem;

-- Nhập liệu cho roles
INSERT INTO roles VALUES
('ADMIN'),
('STAFF'),
('CUSTOMER');
GO

-- Nhập liệu cho users
INSERT INTO users (username, password, email, enabled) VALUES
('LyAdmin', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'lyadmin@gmail.com', 1),
('LyStaff', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'lystaff@gmail.com', 1),
('LyCustomer', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'lycustomer@gmail.com', 1),

('ThangAdmin', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'thangadmin@gmail.com', 1),
('ThangStaff', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'thangstaff@gmail.com', 1),
('ThangCustomer', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'thangcustomer@gmail.com', 1),

('ThienAdmin', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'thienadmin@gmail.com', 1),
('ThienStaff', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'thienstaff@gmail.com', 1),
('ThienCustomer', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'thiencustomer@gmail.com', 1),

('QuanAdmin', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'quanadmin@gmail.com', 1),
('QuanStaff', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'quanstaff@gmail.com', 1),
('QuanCustomer', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'quancustomer@gmail.com', 1),

('KietAdmin', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'kietadmin@gmail.com', 1),
('KietStaff', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'kietstaff@gmail.com', 1),
('KietCustomer', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'kietcustomer@gmail.com', 1),

('MyAdmin', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'myadmin@admin.com', 1),
('MyStaff', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'mystaff@gmail.com', 1),
('MyCustomer', '$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli', 'mycustomer@gmail.com', 1);
GO

-- Gán roles cho users
INSERT INTO user_roles (username, role_id)
SELECT username, 1 FROM users WHERE username LIKE '%Admin';
GO

INSERT INTO user_roles (username, role_id)
SELECT username, 2 FROM users WHERE username LIKE '%Staff';
GO

INSERT INTO user_roles (username, role_id)
SELECT username, 3 FROM users WHERE username LIKE '%Customer';
GO

-- Dữ liệu user_profiles
INSERT INTO user_profiles (username, full_name, gender, phone, address, birthday, avatar_url)
VALUES
('LyAdmin', N'Nguyễn Thành Lý', 1,'0901000001', N'Bình Thạnh, TP.HCM', '2000-03-16', 'avatar.jpg'),
('LyStaff', N'Nguyễn Thành Lý', 1,'0901000002', N'Bình Thạnh, TP.HCM', '2000-03-16', 'avatar.jpg'),
('LyCustomer', N'Nguyễn Thành Lý', 1,'0901000003', N'Bình Thạnh, TP.HCM', '2000-03-16', 'avatar.jpg'),

('ThangAdmin', N'Trương Cẩm Thắng', 1,'0902000001', N'Quận 3, TP.HCM', '1997-08-21', 'avatar.jpg'),
('ThangStaff', N'Trương Cẩm Thắng', 1,'0902000002', N'Quận 3, TP.HCM', '1997-08-21', 'avatar.jpg'),
('ThangCustomer', N'Trương Cẩm Thắng', 1,'0902000003', N'Quận 3, TP.HCM', '1997-08-21', 'avatar.jpg'),

('ThienAdmin', N'Trần Lê Duy Thiện', 1,'0903000001', N'Tân Bình, TP.HCM', '2002-08-25', 'avatar.jpg'),
('ThienStaff', N'Trần Lê Duy Thiện', 1,'0903000002', N'Tân Bình, TP.HCM', '2002-08-25', 'avatar.jpg'),
('ThienCustomer', N'Trần Lê Duy Thiện', 1,'0903000003', N'Tân Bình, TP.HCM', '2002-08-25', 'avatar.jpg'),

('QuanAdmin', N'Nguyễn Khắc Quân', 1,'0904000001', N'Thủ Đức, TP.HCM', '1999-05-30', 'avatar.jpg'),
('QuanStaff', N'Nguyễn Khắc Quân', 1,'0904000002', N'Thủ Đức, TP.HCM', '1999-05-30', 'avatar.jpg'),
('QuanCustomer', N'Nguyễn Khắc Quân', 1,'0904000003', N'Thủ Đức, TP.HCM', '1999-05-30', 'avatar.jpg'),

('KietAdmin', N'Đinh Anh Kiệt', 1,'0905000001', N'Gò Vấp, TP.HCM', '2002-02-11', 'avatar.jpg'),
('KietStaff', N'Đinh Anh Kiệt', 1,'0905000002', N'Gò Vấp, TP.HCM', '2002-02-11', 'avatar.jpg'),
('KietCustomer', N'Đinh Anh Kiệt', 1,'0905000003', N'Gò Vấp, TP.HCM', '2002-02-11', 'avatar.jpg'),

('MyAdmin', N'Lê Hải My', 0,'0906000001', N'Phú Nhuận, TP.HCM', '1997-01-13', 'avatar.jpg'),
('MyStaff', N'Lê Hải My', 0,'0906000002', N'Phú Nhuận, TP.HCM', '1997-01-13', 'avatar.jpg'),
('MyCustomer', N'Lê Hải My', 0,'0906000003', N'Phú Nhuận, TP.HCM', '1997-01-13', 'avatar.jpg');
GO


SELECT * FROM roles
SELECT * FROM users
SELECT * FROM user_roles
SELECT * FROM user_profiles

SELECT username, email, password FROM users WHERE email = 'lycustomer@gmail.com';