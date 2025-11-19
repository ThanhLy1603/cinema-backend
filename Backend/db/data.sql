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


-- Nhập liệu cho bảng categories
INSERT INTO categories (name, is_deleted)
VALUES 
(N'Hành động', 0),
(N'Khoa học viễn tưởng', 0),
(N'Giả tưởng', 0),
(N'Kịch tính', 0),
(N'Tâm lý', 0),
(N'Hài hước', 0),
(N'Kinh dị', 0),
(N'Lãng mạn', 0),
(N'Phiêu lưu', 0),
(N'Gia đình', 0),
(N'Hoạt hình', 0),
(N'Tội phạm', 0),
(N'Bí ẩn', 0),
(N'Tài liệu', 0);

-- Nhập liệu cho bảng films
INSERT INTO films (name, country, director, actor, description, duration, poster, trailer, release_date, status, is_deleted)
VALUES 
-- 1. 5 Centimeters Per Second (5cms)
(N'5 Centimeters Per Second', N'Nhật Bản', N'Makoto Shinkai', N'Kenji Mizuhashi, Yoshimi Kondo', 
    N'Một câu chuyện tình yêu lãng mạn buồn bã được kể qua ba chương, theo chân Takaki Tono từ thời thơ ấu đến khi trưởng thành. Bộ phim khám phá khoảng cách về không gian, thời gian và cảm xúc, cùng nỗi cô đơn dai dẳng khi ta cố gắng giữ chặt những ký ức và kết nối đã mất.', 63, 
    '5cms_poster.webp', '5cms_trailer.mp4', '2007-03-03', 'active', 0),

-- 2. Alice in Borderland 3 (Giả định)
(N'Alice in Borderland 3', N'Nhật Bản', N'Shinsuke Sato', N'Kento Yamazaki, Tao Tsuchiya', 
    N'Sau khi trở về từ vùng biên giới, những người sống sót phải đối mặt với một thực tế mới đầy bí ẩn và tàn khốc hơn. Trò chơi không kết thúc, nó chỉ chuyển sang một cấp độ mới với những quân bài Joker và các luật lệ sinh tồn khắc nghiệt hơn bao giờ hết, buộc họ phải hy sinh tất cả.', 120, 
    'Alice_in_border_land_3_poster.webp', 'Alice_in_borderland_3_trailer.mp4', '2025-10-01', 'upcoming', 0),

-- 3. Avatar 3 (Giả định)
(N'Avatar 3', N'Mỹ', N'James Cameron', N'Sam Worthington, Zoe Saldaña', 
    N'Phần tiếp theo của siêu phẩm khoa học viễn tưởng này sẽ đưa khán giả đi sâu hơn vào thế giới Pandora huyền ảo. Khám phá các nền văn hóa Na’vi mới, đối mặt với mối đe dọa sinh thái từ con người và tìm kiếm sự cân bằng giữa các bộ lạc nước, lửa và gió.', 192, 
    'Avatar_3_poster.jpg', 'Avatar_3_trailer.mp4', '2025-12-19', 'upcoming', 0),

-- 4. Bố Già 5 Siêu Đẳng Cấp (Giả định dựa trên tên file)
('Bố Già 5 Siêu Đẳng Cấp', N'Hàn Quốc', N'Kang Hyoung Chul', N'Yoo Ah In, Ra Mi Ran, Ahn Jae Hong, Park Jin Young, Kim Hee Won', 
    N'Câu chuyện về một gia đình mafia cố gắng duy trì quyền lực trong thế giới ngầm đầy biến động. Khi một kẻ thù cũ quay lại tìm cách trả thù, ông trùm phải dùng mọi cách để bảo vệ đế chế và những người thân yêu, bất kể phải trả giá đắt như thế nào.', 135, 
    'Bo_5_sieu_dang_cap_poster.jpg', 'Bo_5_sieu_dang_cap_trailer.mp4', '2025-05-15', 'upcoming', 0),

-- 5. Búp Bê Sát Nhân (M3GAN)
(N'Búp Bê Sát Nhân', N'Mỹ', N'Gerard Johnstone', N'Allison Williams, Violet McGraw', 
    N'M3GAN là một robot AI giống búp bê, được lập trình để trở thành người bạn đồng hành hoàn hảo cho trẻ em. Tuy nhiên, khi cô bé mồ côi Cady có M3GAN, sự gắn bó của M3GAN với cô bé trở nên cực đoan, dẫn đến những hành vi bạo lực và khó kiểm soát.', 102, 
    'Bup_be_sat_nhan_poster.jpg', 'Bup_be_sat_nhan_trailer.mp4', '2023-01-06', 'active', 0),
    
-- 6. Conan Movie 20 (The Darkest Nightmare)
(N'Conan Movie 20: The Darkest Nightmare', N'Nhật Bản', N'Kobun Shizuno', N'Minami Takayama, Kappei Yamaguchi', 
    N'Tổ chức Áo đen bí ẩn quay lại và gây ra hàng loạt vụ án phức tạp. Conan và đội thám tử nhí phải hợp tác cùng FBI và cảnh sát để ngăn chặn một thảm họa toàn cầu, trong khi danh tính thật của Haibara Ai đang bị đe dọa.', 112, 
    'Conan_movie_20_poster.jpg', 'Conan_movie_20_trailer.mp4', '2016-04-16', 'active', 0),

-- 7. Deadpool 3 (Giả định)
(N'Deadpool 3', N'Mỹ', N'Shawn Levy', N'Ryan Reynolds, Hugh Jackman', 
    N'Wade Wilson/Deadpool bị kéo vào một nhiệm vụ đa vũ trụ cùng với Wolverine. Cặp đôi bất đắc dĩ này phải hợp tác để cứu lấy thực tại. Đây là một bộ phim hành động hài bạo lực đặc trưng với tính cách lém lỉnh, phá vỡ bức tường thứ tư của Deadpool.', 127, 
    'Deadpool_3_Poster.jpg', 'Deadpool_3_trailer.mp4', '2024-07-26', 'active', 0),

-- 8. Death Race 3
(N'Death Race 3: Inferno', N'Mỹ', N'Roel Reiné', N'Luke Goss, Danny Trejo', 
    N'Trong phần ba của loạt phim cuộc đua tử thần, tay đua huyền thoại Carl Lucas/Frankenstein phải đối mặt với thử thách cuối cùng tại sa mạc Kalahari khắc nghiệt. Anh phải thắng cuộc đua này để giành tự do cho chính mình và đội ngũ của mình.', 105, 
    'Death_race_3_poster.jpg', 'Death_race_3_trailer.mp4', '2013-01-22', 'inactive', 0),

-- 9. Doraemon Movie 44 (Giả định)
(N'Doraemon Movie 44: Nobita''s Earth Symphony', N'Nhật Bản', N'Kazuaki Imai', N'Wasabi Mizuta, Megumi Ohara', 
    N'Doraemon và Nobita tham gia vào một cuộc phiêu lưu xuyên không gian và thời gian để giúp một cô bé đến từ tương lai. Họ phải bảo vệ một phát minh quan trọng khỏi tay những kẻ xấu đang cố gắng thay đổi lịch sử thế giới.', 108, 
    'Doraemon_movie_44_poster.jpg', 'Doraemon_movie_44_trailer.mp4', '2025-03-01', 'upcoming', 0),

-- 10. Fast & Furious 7
(N'Fast and Furious 7', N'Mỹ', N'James Wan', N'Vin Diesel, Paul Walker, Jason Statham', 
    N'Sau khi đánh bại Owen Shaw, nhóm của Dom Toretto bị anh trai của Shaw là Deckard Shaw truy sát để trả thù. Cả đội phải tập hợp lại lần cuối để ngăn chặn Shaw trước khi anh ta tìm ra họ, dẫn đến những màn rượt đuổi và chiến đấu đỉnh cao trên toàn cầu.', 137, 
    'Fast_and_furious_7_poster.jpg', 'Fast_and_furious_7_trailer.mp4', '2015-04-03', 'active', 0),

-- 11. Mad Max (Giả định là Fury Road)
(N'Mad Max: Fury Road', N'Úc/Mỹ', N'George Miller', N'Tom Hardy, Charlize Theron', 
    N'Trong một thế giới hậu tận thế khô cằn, Max Rockatansky bị bắt cóc và sau đó buộc phải hợp tác với Nữ hoàng Furiosa. Họ dẫn đầu một cuộc chạy trốn táo bạo qua sa mạc để giải cứu những người vợ khỏi tay bạo chúa Immortan Joe trong một cuộc chiến sinh tử không ngừng nghỉ.', 120, 
    'Mad_max_poster.jpg', 'Mad_max_trailer.mp4', '2015-05-15', 'active', 0),

-- 12. Mắt Biếc
(N'Mắt Biếc', N'Việt Nam', N'Victor Vũ', N'Trần Nghĩa, Trúc Anh, Trần Phong', 
    N'Dựa trên tiểu thuyết của Nguyễn Nhật Ánh, bộ phim kể về mối tình đơn phương đầy day dứt của chàng thư sinh Ngạn dành cho cô bạn Mắt Biếc Hà Lan. Bối cảnh từ làng Đo Đo yên bình đến thành phố đầy cám dỗ, nơi tình cảm chân thành phải đối mặt với sự thay đổi của thời gian và số phận.', 117, 
    'Mac_biec_poster.jpeg', 'Mat_biec_trailer.mp4', '2019-12-20', 'active', 0),
    
-- 13. Năm Mười Muơi Lâm (Giả định dựa trên tên file)
(N'Năm Mười Mười Lâm', N'Việt Nam', N'Tấn Hoàng Phong', N'Trần Phong, Huỳnh Tú Uyên, Trần Vân Anh', 
    N'Một bộ phim hài hước, tình cảm về những câu chuyện dở khóc dở cười của một nhóm bạn thân cố gắng thực hiện ước mơ lớn giữa lòng thành phố. Phim là hành trình khám phá bản thân và sự quan trọng của tình bạn và gia đình trong những năm tháng tuổi trẻ bồng bột.', 100, 
    'Nam_muoi_muoi_lam_poster.jpg', 'Nam_muoi_muoi_lam_trailer.mp4', '2024-03-08', 'upcoming', 0),

-- 14. Nhà Gia Tiên (Tên file: Nha_gia_tien_poster.mp4 - Lỗi tên)
(N'Nhà Gia Tiên', N'Việt Nam', N'Huỳnh Lập', N'Huỳnh Lập, Phương Mỹ Chi', 
    N'Một bộ phim gia đình, hài hước về một người cha cố gắng che giấu tình hình tài chính khó khăn của mình bằng cách giả vờ giàu có. Sự thật bị phanh phui dẫn đến những tình huống trớ trêu, buộc cả gia đình phải đối mặt với thực tế.', 95, 
    'Nha_gia_tien_poster.jpg', 'Nha_gia_tien_poster.mp4', '2023-11-20', 'active', 0), -- Dùng tên file video là poster do lỗi
    
-- 15. Pacific Rim
(N'Pacific Rim', N'Mỹ', N'Guillermo del Toro', N'Charlie Hunnam, Idris Elba', 
    N'Khi những quái vật khổng lồ được gọi là Kaiju trỗi dậy từ đáy biển để tiêu diệt nhân loại, con người phải chế tạo ra các robot chiến đấu khổng lồ gọi là Jaeger. Phim kể về cuộc chiến tuyệt vọng của những phi công điều khiển Jaeger để cứu lấy Trái Đất.', 131, 
    'Pacific_rim_poster.webp', 'Pacific_rim_trailer.mp4', '2013-07-12', 'active', 0),

-- 16. Spirited Away
(N'Spirited Away', N'Nhật Bản', N'Hayao Miyazaki', N'Rumi Hiiragi, Miyu Irino', 
    N'Cô bé Chihiro bị lạc vào thế giới linh hồn và phải làm việc tại một nhà tắm công cộng để giải cứu cha mẹ đã bị biến thành heo. Đây là một câu chuyện cổ tích hiện đại đầy mê hoặc về lòng can đảm, sự trưởng thành và tình yêu thương.', 125, 
    'Spirited_away_poster.webp', 'Spirited_away_trailer.mp4', '2001-07-20', 'active', 0),
    
-- 17. Tenki no Ko (Weathering with You)
(N'Weathering with You', N'Nhật Bản', N'Makoto Shinkai', N'Kotaro Daigo, Nana Mori', 
    N'Hodaka, một cậu bé bỏ nhà lên Tokyo, gặp gỡ Hina, một cô gái có khả năng thay đổi thời tiết. Mối tình lãng mạn của họ gắn liền với những trận mưa bất tận tại Tokyo, đặt ra câu hỏi về cái giá phải trả cho một điều ước siêu nhiên.', 112, 
    'Tenki_no_ko_poster.jpg', 'Tenki_no_ko_trailer.mp4', '2019-07-19', 'active', 0),
    
-- 18. Tôi Thấy Hoa Vàng Trên Cỏ Xanh
(N'Tôi Thấy Hoa Vàng Trên Cỏ Xanh', N'Việt Nam', N'Victor Vũ', N'Thịnh Vinh, Trọng Khang, Thanh Mỹ', 
    N'Dựa trên truyện dài cùng tên, phim là thước phim điện ảnh đẹp đẽ về ký ức tuổi thơ của hai anh em Thiều và Tường tại một làng quê nghèo. Câu chuyện nhẹ nhàng, trong trẻo nhưng đầy rung động về tình anh em, tình bạn và những khám phá đầu đời.', 103, 
    'Toi_thay_hoa_vang_tren_co_xanh_poster.jpg', 'Toi_thay_hoa_vang_tren_co_xanh_trailer.mp4', '2015-10-02', 'active', 0),

-- 19. Tù Chiến Trên Không (Con Air)
(N'Tử Chiến Trên Không', N'Mỹ', N'Lê Nhật Quang', N'Thái Hoà, Kaity Nguyễn, Thanh Sơn, Xuân Phúc',
    N'"Tử Chiến Trên Không" kể về Bình, chuyên viên cảnh vệ hàng không, vô tình rơi vào cuộc đối đầu sinh tử khi chuyến bay anh đi bị nhóm không tặc do Long cầm đầu khống chế. Trong 15 phút sau khi cất cánh, máy bay trở thành chiến trường. Bình cùng phi hành đoàn và hành khách phải phối hợp chống trả, ngăn chặn âm mưu tàn độc của bọn cướp, bảo vệ tính mạng mọi người giữa bầu trời không lối thoát.', 115,
    'Tu_chien_tren_khong_poster.jpg', 'Tu_chien_tren_khong_trailer.mp4', '1997-06-06', 'inactive', 0),
    
-- 20. Your Name
(N'Your Name', N'Nhật Bản', N'Makoto Shinkai', N'Ryunosuke Kamiki, Mone Kamishiraishi', 
    N'Phim kể về Mitsuha và Taki, hai học sinh trung học ở hai nơi khác nhau, bị hoán đổi cơ thể một cách bí ẩn trong giấc mơ. Khi họ cố gắng tìm kiếm và gặp gỡ nhau, một sự kiện thiên văn thảm khốc đe dọa chia cắt họ mãi mãi, buộc họ phải chạy đua với thời gian.', 107, 
    'Your_name_poster.jpeg', 'Your_name_trailer.mp4', '2016-08-26', 'active', 0);


-- Nhập liệu cho bảng film_categories
INSERT INTO film_categories (film_id, category_id)
SELECT F.id, C.id
FROM films AS F
CROSS JOIN categories AS C
WHERE (F.name = N'5 Centimeters Per Second' AND C.name IN (N'Tình Cảm', N'Chính Kịch'))
   OR (F.name = N'Alice in Borderland 3' AND C.name IN (N'Hành Động', N'Khoa Học Viễn Tưởng', N'Chính Kịch'))
   OR (F.name = N'Avatar 3' AND C.name IN (N'Khoa Học Viễn Tưởng', N'Hành Động', N'Phiêu Lưu'))
   OR (F.name = N'Bố Già 5 Siêu Đẳng Cấp' AND C.name IN (N'Hành Động', N'Hài hước'))
   OR (F.name = N'Búp Bê Sát Nhân' AND C.name IN (N'Kinh Dị', N'Hành Động'))
   OR (F.name = N'Conan Movie 20: The Darkest Nightmare' AND C.name IN (N'Hoạt Hình', N'Trinh Thám', N'Hành Động'))
   OR (F.name = N'Deadpool 3' AND C.name IN (N'Hành Động', N'Hài', N'Khoa Học Viễn Tưởng'))
   OR (F.name = N'Death Race 3: Inferno' AND C.name IN (N'Hành Động', N'Khoa Học Viễn Tưởng'))
   OR (F.name = N'Doraemon Movie 44: Nobita''s Earth Symphony' AND C.name IN (N'Hoạt Hình', N'Gia Đình', N'Phiêu Lưu'))
   OR (F.name = N'Fast and Furious 7' AND C.name IN (N'Hành Động', N'Phiêu Lưu'))
   OR (F.name = N'Mắt Biếc' AND C.name IN (N'Tình Cảm', N'Chính Kịch'))
   OR (F.name = N'Năm Mười Mười Lâm' AND C.name IN (N'Hài', N'Gia Đình'))
   OR (F.name = N'Nhà Gia Tiên' AND C.name IN (N'Tâm Linh', N'Kinh Dị'))
   OR (F.name = N'Pacific Rim' AND C.name IN (N'Khoa Học Viễn Tưởng', N'Hành Động'))
   OR (F.name = N'Spirited Away' AND C.name IN (N'Hoạt Hình', N'Giả Tưởng', N'Phiêu Lưu'))
   OR (F.name = N'Weathering with You' AND C.name IN (N'Hoạt Hình', N'Tình Cảm', N'Giả Tưởng'))
   OR (F.name = N'Tôi Thấy Hoa Vàng Trên Cỏ Xanh' AND C.name IN (N'Gia Đình', N'Chính Kịch', N'Tình Cảm'))
   OR (F.name = N'Tử Chiến Trên Không' AND C.name IN (N'Hành Động', N'Chính Kịch'))
   OR (F.name = N'Your Name' AND C.name IN (N'Hoạt Hình', N'Tình Cảm', N'Giả Tưởng'));

-- Nhập liệu cho bảng seat_types
INSERT INTO seat_types (name, is_deleted)
VALUES 
(N'Ghế Thường', 0),
(N'Ghế VIP', 0),
(N'Ghế Couple', 0);
GO

-- Nhập liệu cho bảng rooms
INSERT INTO rooms (name, status, is_deleted)
VALUES
(N'Phòng 1', 'active', 0),
(N'Phòng 2', 'active', 0),
(N'Phòng 3', 'maintenance', 0),
(N'Phòng VIP 1', 'active', 0);

-- Nhập liệu cho bảng show_times
INSERT INTO show_times (start_time)
VALUES
('07:00'),
('08:00'),
('09:00'),
('10:00'),
('11:00'),
('12:00'),
('13:00'),
('14:00'),
('15:00'),
('16:00'),
('17:00'),
('18:00'),
('19:00'),
('20:00'),
('21:00'),
('22:00'),
('23:00'),
('00:00');

-- Nhập liệu cho bảng ghế
-- Bước 1 & 2: Lấy IDs (Giữ nguyên như trước)
-- (Code lấy @RoomIDs và @SeatTypeIDs được lược bỏ để tập trung vào phần thay đổi)

DECLARE @Rooms TABLE (room_name NVARCHAR(50), room_id UNIQUEIDENTIFIER);
INSERT INTO @Rooms (room_name, room_id)
SELECT name, id FROM rooms WHERE is_deleted = 0;

DECLARE @Room1ID UNIQUEIDENTIFIER, @Room2ID UNIQUEIDENTIFIER, @Room3ID UNIQUEIDENTIFIER, @RoomVIP1ID UNIQUEIDENTIFIER;
SELECT @Room1ID = room_id FROM @Rooms WHERE room_name = N'Phòng 1';
SELECT @Room2ID = room_id FROM @Rooms WHERE room_name = N'Phòng 2';
SELECT @Room3ID = room_id FROM @Rooms WHERE room_name = N'Phòng 3';
SELECT @RoomVIP1ID = room_id FROM @Rooms WHERE room_name = N'Phòng VIP 1';

DECLARE @SeatTypes TABLE (type_name NVARCHAR(30), type_id UNIQUEIDENTIFIER);
INSERT INTO @SeatTypes (type_name, type_id)
SELECT name, id FROM seat_types WHERE is_deleted = 0;

DECLARE @ThuongID UNIQUEIDENTIFIER, @VipID UNIQUEIDENTIFIER, @CoupleID UNIQUEIDENTIFIER;
SELECT @ThuongID = type_id FROM @SeatTypes WHERE type_name = N'Ghế Thường';
SELECT @VipID = type_id FROM @SeatTypes WHERE type_name = N'Ghế VIP';
SELECT @CoupleID = type_id FROM @SeatTypes WHERE type_name = N'Ghế Couple';


-- Bước 3: Tạo Bảng tạm để lưu trữ cấu trúc ghế với Vị trí đầy đủ (Position + Số ghế)
DECLARE @SeatLayout TABLE (
    Position NVARCHAR(10) PRIMARY KEY, -- Ví dụ: A1, D48, K151
    Seat_Type_ID UNIQUEIDENTIFIER
);

-- Hàm ánh xạ số ghế sang hàng chữ cái
WITH SeatMapping AS (
    SELECT
        number,
        CASE
            WHEN number BETWEEN 1 AND 15 THEN 'A'
            WHEN number BETWEEN 16 AND 30 THEN 'B'
            WHEN number BETWEEN 31 AND 45 THEN 'C'
            WHEN number BETWEEN 46 AND 60 THEN 'D'
            WHEN number BETWEEN 61 AND 75 THEN 'E'
            WHEN number BETWEEN 76 AND 90 THEN 'F'
            WHEN number BETWEEN 91 AND 105 THEN 'G'
            WHEN number BETWEEN 106 AND 120 THEN 'H'
            WHEN number BETWEEN 121 AND 135 THEN 'I'
            WHEN number BETWEEN 136 AND 150 THEN 'J'
            WHEN number BETWEEN 151 AND 156 THEN 'K'
        END AS RowLetter,
        CASE
            -- Ghế Thường (Trắng)
            WHEN (number BETWEEN 1 AND 48 AND number NOT IN (49, 57)) OR 
                 (number BETWEEN 58 AND 63) OR 
                 (number BETWEEN 73 AND 78) OR 
                 (number BETWEEN 88 AND 93) OR 
                 (number BETWEEN 103 AND 150)
            THEN @ThuongID
            -- Ghế VIP (Vàng)
            WHEN (number BETWEEN 49 AND 57) OR 
                 (number BETWEEN 64 AND 72) OR 
                 (number BETWEEN 79 AND 87) OR 
                 (number BETWEEN 94 AND 102)
            THEN @VipID
            -- Ghế Couple (Xanh)
            WHEN (number BETWEEN 151 AND 156)
            THEN @CoupleID
        END AS Seat_Type_ID
    FROM (
        -- Tạo dãy số từ 1 đến 156
        SELECT TOP 156 ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) AS number
        FROM sys.objects A, sys.objects B, sys.objects C
    ) AS Numbers
)
-- Chèn dữ liệu vào bảng tạm
INSERT INTO @SeatLayout (Position, Seat_Type_ID)
SELECT
    RowLetter + CAST(number AS NVARCHAR(10)) AS Position,
    Seat_Type_ID
FROM SeatMapping
WHERE Seat_Type_ID IS NOT NULL -- Đảm bảo không có lỗi logic

-- Kiểm tra một số vị trí ví dụ:
-- SELECT * FROM @SeatLayout WHERE Position IN ('A1', 'C45', 'D48', 'E72', 'K151') ORDER BY Position;


-- Bước 4: Nhập liệu vào bảng seats cho TỪNG PHÒNG
INSERT INTO seats (room_id, seat_type_id, position)
SELECT @Room1ID, Seat_Type_ID, Position FROM @SeatLayout ORDER BY Position;

INSERT INTO seats (room_id, seat_type_id, position)
SELECT @Room2ID, Seat_Type_ID, Position FROM @SeatLayout ORDER BY Position;

INSERT INTO seats (room_id, seat_type_id, position)
SELECT @Room3ID, Seat_Type_ID, Position FROM @SeatLayout ORDER BY Position;

INSERT INTO seats (room_id, seat_type_id, position)
SELECT @RoomVIP1ID, Seat_Type_ID, Position FROM @SeatLayout ORDER BY Position;

-- Hiển thị kết quả nhập liệu để kiểm tra
SELECT 
    R.name AS RoomName, 
    S.position, 
    ST.name AS SeatTypeName
FROM seats S
JOIN rooms R ON S.room_id = R.id
JOIN seat_types ST ON S.seat_type_id = ST.id
ORDER BY R.name, S.position;

DELETE FROM seats

-- Nhập liệu cho bảng schedules

-- Chèn dữ liệu mới
DECLARE @FilmIDs TABLE (RowNum INT, id UNIQUEIDENTIFIER);
INSERT INTO @FilmIDs (RowNum, id)
SELECT ROW_NUMBER() OVER (ORDER BY name), id FROM films WHERE is_deleted = 0;

DECLARE @RoomIDs TABLE (RowNum INT, id UNIQUEIDENTIFIER);
INSERT INTO @RoomIDs (RowNum, id)
SELECT ROW_NUMBER() OVER (ORDER BY name), id FROM rooms WHERE is_deleted = 0;

DECLARE @TimeIDs TABLE (RowNum INT, id UNIQUEIDENTIFIER);
INSERT INTO @TimeIDs (RowNum, id)
SELECT ROW_NUMBER() OVER (ORDER BY start_time), id FROM show_times WHERE is_deleted = 0;

DECLARE @FilmCount INT = (SELECT COUNT(*) FROM @FilmIDs);
DECLARE @RoomCount INT = (SELECT COUNT(*) FROM @RoomIDs);
DECLARE @TimeCount INT = (SELECT COUNT(*) FROM @TimeIDs);

DECLARE @d INT = 0;
WHILE @d < 7  -- 7 ngày tới
BEGIN
    DECLARE @date DATE = DATEADD(DAY, @d, CAST(GETDATE() AS DATE));
    DECLARE @r INT = 1;

    WHILE @r <= @RoomCount
    BEGIN
        DECLARE @RoomId UNIQUEIDENTIFIER = (SELECT id FROM @RoomIDs WHERE RowNum = @r);
        DECLARE @t INT = 1;

        WHILE @t <= @TimeCount
        BEGIN
            DECLARE @FilmRow INT = ((@r + @t + @d) % @FilmCount) + 1;
            DECLARE @FilmId UNIQUEIDENTIFIER = (SELECT id FROM @FilmIDs WHERE RowNum = @FilmRow);
            DECLARE @TimeId UNIQUEIDENTIFIER = (SELECT id FROM @TimeIDs WHERE RowNum = @t);

            -- Chèn dữ liệu
            INSERT INTO schedules (film_id, room_id, show_time_id, schedule_date, is_deleted)
            VALUES (@FilmId, @RoomId, @TimeId, @date, 0);

            SET @t += 1;
        END

        SET @r += 1;
    END

    SET @d += 1;
END;

SELECT COUNT(*) AS TotalInserted FROM schedules;

-- Kiểm tra
SELECT COUNT(*) AS TotalInserted,
       '✅ Lịch chiếu đã nhập thành công, không trùng khóa.' AS Result
FROM schedules
WHERE schedule_date = @StartDate;
GO

SELECT 'Đã nhập liệu thành công 80 bản ghi mới cho bảng schedules.' AS Result;

-- Nhập liệu cho bảng foods
INSERT INTO products (name, description, poster, is_deleted)
VALUES
(N'Aquafina', N'01 chai nước suối Aquafina 500ml. Nhận trong ngày xem phim', N'Aquafina_poster.png', 0),
(N'Pepsi 2020z', N'01 nước Pepsi 200z. Nhận trong ngày xem phim', N'Pepsi_220z_poster.png', 0),
(N'Bắp rang vị ngọt 440z', N'01 bắp 440z vị ngọt. Nhận trong ngày xem phim', N'Bap_ngot_poster.png', 0),
(N'Bắp rang vị phô mai 440z', N'01 bắp 440z vị phô mai. Nhận trong ngày xem phim', N'Bap_pho_mai_poster.png', 0),
(N'Combo 2 xúc xích - 1 bắp ngọt 440z - 1 Pepsi 220z', N'01 bắp lớn vị ngọt + 01 pepsi 220z + 01 xúc xích phô mai. Nhận trong ngày xem phim', N'Combo_bapngot_pepsi_xucxich_poster.png', 0);


-- Nhập liệu cho bảng product_prices
-- Ngày bắt đầu áp dụng giá
DECLARE @Today DATE = '2025-11-14'; 

INSERT INTO product_prices (product_id, price, start_date, end_date, is_deleted)
SELECT 
    P.id,
    CASE P.name
        WHEN N'Aquafina' THEN 20000.00
        WHEN N'Pepsi 220z' THEN 25000.00               -- *** Đã sửa từ '2020z' thành '220z' ***
        WHEN N'Bắp rang vị ngọt 440z' THEN 60000.00
        WHEN N'Bắp rang vị phô mai 440z' THEN 65000.00
        WHEN N'Combo 2 xúc xích - 1 bắp ngọt 440z - 1 Pepsi 220z' THEN 110000.00
        ELSE NULL -- Trường hợp không khớp tên, giá sẽ là NULL và bị loại bỏ ở WHERE
    END AS price,
    @Today AS start_date,
    NULL AS end_date,
    0 AS is_deleted
FROM products P
WHERE P.name IN (
    N'Aquafina', 
    N'Pepsi 220z',                                     -- *** Đã sửa từ '2020z' thành '220z' ***
    N'Bắp rang vị ngọt 440z', 
    N'Bắp rang vị phô mai 440z', 
    N'Combo 2 xúc xích - 1 bắp ngọt 440z - 1 Pepsi 220z'
)
-- Điều kiện này đảm bảo chỉ những sản phẩm có giá được định nghĩa trong CASE (Tức là không NULL) mới được chèn.
AND (CASE P.name
        WHEN N'Aquafina' THEN 1 
        WHEN N'Pepsi 220z' THEN 1
        WHEN N'Bắp rang vị ngọt 440z' THEN 1
        WHEN N'Bắp rang vị phô mai 440z' THEN 1
        WHEN N'Combo 2 xúc xích - 1 bắp ngọt 440z - 1 Pepsi 220z' THEN 1
        ELSE 0 
    END) = 1;
GO

-- Nhập liệu cho bảng promotions, promotion_rules, promotion_items

-- Nhập liệu 10 chương trình khuyến mãi

-- 1. Giảm 10% tất cả vé phim
INSERT INTO promotions (name, description, discount_percent, start_date, end_date, active, is_deleted)
VALUES (N'Giảm 10% vé phim', N'Giảm 10% tất cả vé phim', 10, '2025-11-15', '2025-12-31', 1, 0);

INSERT INTO promotion_items (promotion_id, note)
VALUES ((SELECT id FROM promotions WHERE name=N'Giảm 10% vé phim'), N'Áp dụng cho tất cả phim');

INSERT INTO promotion_rules (promotion_id, rule_type, rule_value)
VALUES ((SELECT id FROM promotions WHERE name=N'Giảm 10% vé phim'), 'PERCENT', '{"percent":10}');

-- 2. Mua 3 món 79k (combo ăn uống)
INSERT INTO promotions (name, description, start_date, end_date, active, is_deleted)
VALUES (N'Mua 3 món 79k', N'Combo ăn uống: Popcorn + Soda + Nuggets chỉ 79.000đ', '2025-11-15', '2025-12-31', 1, 0);

INSERT INTO promotion_items (promotion_id, product_id, note)
VALUES 
((SELECT id FROM promotions WHERE name=N'Mua 3 món 79k'), (SELECT id FROM products WHERE name=N'Bắp rang vị ngọt 440z'), N'Combo 3 món'),
((SELECT id FROM promotions WHERE name=N'Mua 3 món 79k'), (SELECT id FROM products WHERE name=N'Pepsi 2020z'), N'Combo 3 món'),
((SELECT id FROM promotions WHERE name=N'Mua 3 món 79k'), (SELECT id FROM products WHERE name=N'Aquafina'), N'Combo 3 món');

INSERT INTO promotion_rules (promotion_id, rule_type, rule_value)
VALUES ((SELECT id FROM promotions WHERE name=N'Mua 3 món 79k'), 'FIXED_COMBO', N'{"items":["Bắp rang vị ngọt 440z","Pepsi 2020z","Aquafina"],"price":79000}');

-- 3. Mua 2 tặng 1 nước ngọt
INSERT INTO promotions (name, description, start_date, end_date, active, is_deleted)
VALUES (N'Mua 2 tặng 1 nước ngọt', N'Mua 2 nước ngọt tặng 1', '2025-11-15', '2025-12-31', 1, 0);

INSERT INTO promotion_items (promotion_id, product_id, note)
VALUES 
((SELECT id FROM promotions WHERE name=N'Mua 2 tặng 1 nước ngọt'), (SELECT id FROM products WHERE name=N'Pepsi 2020z'), N'Mua 2 tặng 1'),
((SELECT id FROM promotions WHERE name=N'Mua 2 tặng 1 nước ngọt'), (SELECT id FROM products WHERE name=N'Aquafina'), N'Mua 2 tặng 1');

INSERT INTO promotion_rules (promotion_id, rule_type, rule_value)
VALUES ((SELECT id FROM promotions WHERE name=N'Mua 2 tặng 1 nước ngọt'), 'BUY_X_GET_Y', N'{"buy":2,"get":1}');

-- 4. Combo Fast & Furious 7 + Popcorn lớn
INSERT INTO promotions (name, description, start_date, end_date, active, is_deleted)
VALUES (N'Combo Fast & Furious + Popcorn', N'Vé phim Fast & Furious 7 + Popcorn lớn chỉ 150.000đ', '2025-11-15', '2025-12-31', 1, 0);

INSERT INTO promotion_items (promotion_id, film_id, product_id, note)
VALUES 
((SELECT id FROM promotions WHERE name=N'Combo Fast & Furious + Popcorn'), (SELECT id FROM films WHERE name=N'Fast and Furious 7'), (SELECT id FROM products WHERE name=N'Bắp rang vị ngọt 440z'), N'Combo phim + bắp');

INSERT INTO promotion_rules (promotion_id, rule_type, rule_value)
VALUES ((SELECT id FROM promotions WHERE name=N'Combo Fast & Furious + Popcorn'), 'FIXED_COMBO', N'{"items":["Fast and Furious 7","Bắp rang vị ngọt 440z"],"price":150000}');

-- 5. Vé Avatar 3 giảm 15%
INSERT INTO promotions (name, description, discount_percent, start_date, end_date, active, is_deleted)
VALUES (N'Giảm 15% vé Avatar 3', N'Giảm 15% vé phim Avatar 3', 15, '2025-12-19', '2025-12-31', 1, 0);

INSERT INTO promotion_items (promotion_id, film_id, note)
VALUES ((SELECT id FROM promotions WHERE name=N'Giảm 15% vé Avatar 3'), (SELECT id FROM films WHERE name=N'Avatar 3'), N'Giảm riêng vé Avatar 3');

INSERT INTO promotion_rules (promotion_id, rule_type, rule_value)
VALUES ((SELECT id FROM promotions WHERE name=N'Giảm 15% vé Avatar 3'), N'PERCENT', N'{"percent":15}');

-- 6. Combo Alice in Borderland 3 + Pepsi
INSERT INTO promotions (name, description, start_date, end_date, active, is_deleted)
VALUES (N'Combo Alice + Pepsi', N'Vé Alice in Borderland 3 + Pepsi 2020z chỉ 120.000đ', '2025-10-01', '2025-12-31', 1, 0);

INSERT INTO promotion_items (promotion_id, film_id, product_id, note)
VALUES 
((SELECT id FROM promotions WHERE name=N'Combo Alice + Pepsi'), (SELECT id FROM films WHERE name=N'Alice in Borderland 3'), (SELECT id FROM products WHERE name=N'Pepsi 2020z'), N'Combo phim + nước');

INSERT INTO promotion_rules (promotion_id, rule_type, rule_value)
VALUES ((SELECT id FROM promotions WHERE name=N'Combo Alice + Pepsi'), 'FIXED_COMBO', N'{"items":["Alice in Borderland 3","Pepsi 2020z"],"price":120000}');

-- 7. Giảm 20% vé Mắt Biếc
INSERT INTO promotions (name, description, discount_percent, start_date, end_date, active, is_deleted)
VALUES (N'Giảm 20% vé Mắt Biếc', N'Giảm 20% vé phim Mắt Biếc', 20, '2025-11-15', '2025-12-31', 1, 0);

INSERT INTO promotion_items (promotion_id, film_id, note)
VALUES ((SELECT id FROM promotions WHERE name=N'Giảm 20% vé Mắt Biếc'), (SELECT id FROM films WHERE name=N'Mắt Biếc'), N'Giảm riêng vé Mắt Biếc');

INSERT INTO promotion_rules (promotion_id, rule_type, rule_value)
VALUES ((SELECT id FROM promotions WHERE name=N'Giảm 20% vé Mắt Biếc'), N'PERCENT', N'{"percent":20}');

-- 8. Mua 1 vé Conan Movie 20 + 1 Aquafina giảm 50% Aquafina
INSERT INTO promotions (name, description, start_date, end_date, active, is_deleted)
VALUES (N'Combo Conan + Nước', N'Mua 1 vé Conan Movie 20 + 1 Aquafina giảm 50% Aquafina', '2025-11-15', '2025-12-31', 1, 0);

INSERT INTO promotion_items (promotion_id, film_id, product_id, note)
VALUES 
((SELECT id FROM promotions WHERE name=N'Combo Conan + Nước'), (SELECT id FROM films WHERE name=N'Conan Movie 20: The Darkest Nightmare'), (SELECT id FROM products WHERE name=N'Aquafina'), N'Combo phim + nước');

INSERT INTO promotion_rules (promotion_id, rule_type, rule_value)
VALUES ((SELECT id FROM promotions WHERE name=N'Combo Conan + Nước'), 'PERCENT', N'{"percent":50,"apply_on":"Aquafina"}');

-- 9. Mua 2 Fast & Furious 7 tặng 1 Bắp rang vị phô mai
INSERT INTO promotions (name, description, start_date, end_date, active, is_deleted)
VALUES (N'Mua 2 Fast & Furious 7 tặng 1 bắp phô mai', N'Mua 2 vé Fast & Furious 7 tặng 1 bắp rang vị phô mai', '2025-11-15', '2025-12-31', 1, 0);

INSERT INTO promotion_items (promotion_id, film_id, product_id, note)
VALUES ((SELECT id FROM promotions WHERE name=N'Mua 2 Fast & Furious 7 tặng 1 bắp phô mai'), (SELECT id FROM films WHERE name=N'Fast and Furious 7'), (SELECT id FROM products WHERE name=N'Bắp rang vị phô mai 440z'), N'Mua 2 tặng 1');

INSERT INTO promotion_rules (promotion_id, rule_type, rule_value)
VALUES ((SELECT id FROM promotions WHERE name=N'Mua 2 Fast & Furious 7 tặng 1 bắp phô mai'), 'BUY_X_GET_Y', N'{"buy":2,"get":1,"apply_on":"Bắp rang vị phô mai 440z"}');

-- 10. Combo Tenki no Ko + Pepsi 2020z + Popcorn
INSERT INTO promotions (name, description, start_date, end_date, active, is_deleted)
VALUES (N'Combo Tenki no Ko + Pepsi + Popcorn', N'Vé Tenki no Ko + Pepsi + Bắp rang vị ngọt chỉ 150.000đ', '2025-07-19', '2025-12-31', 1, 0);

INSERT INTO promotion_items (promotion_id, film_id, product_id, note)
VALUES 
((SELECT id FROM promotions WHERE name=N'Combo Tenki no Ko + Pepsi + Popcorn'), (SELECT id FROM films WHERE name=N'Weathering with You'), (SELECT id FROM products WHERE name=N'Pepsi 2020z'), N'Combo phim + nước + bắp'),
((SELECT id FROM promotions WHERE name=N'Combo Tenki no Ko + Pepsi + Popcorn'), (SELECT id FROM films WHERE name=N'Weathering with You'), (SELECT id FROM products WHERE name=N'Bắp rang vị ngọt 440z'), N'Combo phim + nước + bắp');

INSERT INTO promotion_rules (promotion_id, rule_type, rule_value)
VALUES ((SELECT id FROM promotions WHERE name=N'Combo Tenki no Ko + Pepsi + Popcorn'), 'FIXED_COMBO', N'{"items":["Weathering with You","Pepsi 2020z","Bắp rang vị ngọt 440z"],"price":150000}');
GO

-- 11. Giảm 10% tổng hoá đơn nếu mua trên 200.000₫
INSERT INTO promotions (name, description, start_date, end_date, active, is_deleted)
VALUES ('Giảm 10% tổng hóa đơn', 'Áp dụng cho hóa đơn >= 200k', '2025-11-14', '2025-12-31', 1, 0);

DECLARE @promo_id UNIQUEIDENTIFIER = (SELECT id FROM promotions WHERE name='Giảm 10% tổng hóa đơn');

INSERT INTO promotion_rules (promotion_id, rule_type, rule_value)
VALUES (@promo_id, 'TOTAL_PERCENT', N'{"percent":10}');

-- Nhập liệu cho bảng price_tickets
-- Khai báo ngày bắt đầu áp dụng
DECLARE @StartDate DATE = '2025-11-14';
DECLARE @BasePrice DECIMAL(12, 2) = 100000.00;

-- Tạo bảng tạm để chứa tất cả các tổ hợp
-- Bước 1: Lấy tất cả các ID cần thiết
WITH AllCombinations AS (
    SELECT
        F.id AS film_id,
        ST.id AS seat_type_id,
        SH.id AS show_time_id,
        SH.start_time AS show_start_time,
        DT.day_type
    FROM films F
    CROSS JOIN seat_types ST
    CROSS JOIN show_times SH
    CROSS JOIN (
        VALUES 
            (N'WEEKDAY'), 
            (N'WEEKEND'), 
            (N'HOLIDAY'), 
            (N'SPECIAL')
    ) AS DT(day_type)
    -- Chỉ lấy các phim đang 'active' hoặc 'upcoming'
    WHERE F.status IN ('active', 'upcoming')
),
-- Bước 2: Tính toán giá dựa trên các quy tắc đã định
CalculatedPrices AS (
    SELECT
        AC.film_id,
        AC.seat_type_id,
        AC.show_time_id,
        AC.day_type,
        -- Bắt đầu bằng giá cơ sở
        @BasePrice 
        
        -- 1. Điều chỉnh theo LOẠI GHẾ
        + CASE 
            WHEN ST.name = N'Ghế VIP' THEN 20000.00
            WHEN ST.name = N'Ghế Couple' THEN 50000.00 -- Phụ phí cho 2 người
            ELSE 0.00
          END
          
        -- 2. Điều chỉnh theo LOẠI NGÀY
        + CASE AC.day_type
            WHEN 'WEEKEND' THEN 20000.00
            WHEN 'HOLIDAY' THEN 40000.00
            WHEN 'SPECIAL' THEN 60000.00
            ELSE 0.00
          END
          
        -- 3. Điều chỉnh theo GIỜ CHIẾU (Giờ Vàng 19:00 - 22:00)
        + CASE 
            WHEN AC.show_start_time >= '19:00:00' AND AC.show_start_time <= '22:00:00' THEN 10000.00
            ELSE 0.00
          END
          
        -- 4. Điều chỉnh theo PHIM (Phim Bom Tấn)
        + CASE 
            WHEN F.name IN (N'Deadpool 3', N'Avatar 3', N'Fast and Furious 7') THEN 10000.00
            ELSE 0.00
          END
        AS calculated_price
        
    FROM AllCombinations AC
    JOIN films F ON AC.film_id = F.id
    JOIN seat_types ST ON AC.seat_type_id = ST.id
    -- Lọc các tổ hợp đã tồn tại để tránh trùng lặp
    LEFT JOIN price_tickets PT ON 
        AC.film_id = PT.film_id AND 
        AC.seat_type_id = PT.seat_type_id AND 
        AC.show_time_id = PT.show_time_id AND 
        AC.day_type = PT.day_type AND 
        PT.start_date = @StartDate
    WHERE PT.id IS NULL -- Chỉ chèn những quy tắc giá chưa tồn tại với ngày bắt đầu hôm nay
)
-- Bước 3: INSERT dữ liệu vào bảng price_tickets
INSERT INTO price_tickets (film_id, seat_type_id, show_time_id, day_type, price, start_date, end_date, is_deleted)
SELECT 
    film_id,
    seat_type_id,
    show_time_id,
    day_type,
    calculated_price,
    @StartDate,
    NULL,
    0
FROM CalculatedPrices
ORDER BY film_id, day_type, show_time_id, seat_type_id;

-- Thông báo kết quả
SELECT N'Đã hoàn tất nhập liệu hàng loạt cho bảng price_tickets. Số dòng được chèn:' AS Status, @@ROWCOUNT AS RecordsInserted;


-- Nhập liệu cho các bảng hoá đơn


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
SELECT * FROM product_prices


SELECT 
    R.name AS TenPhong,
    F.name AS TenPhim,
    ST.start_time AS ThoiGianBatDau,
    S.schedule_date AS NgayChieu
FROM 
    schedules S
JOIN 
    rooms R ON S.room_id = R.id
JOIN 
    films F ON S.film_id = F.id           -- JOIN với bảng films
JOIN 
    show_times ST ON S.show_time_id = ST.id -- JOIN với bảng show_times
WHERE 
    S.schedule_date = '2025-11-13' 
ORDER BY 
    R.name, 
    ST.start_time;

SELECT 
    R.name AS RoomName,
    COUNT(S.id) AS TotalSchedules
FROM 
    schedules S
JOIN 
    rooms R ON S.room_id = R.id
WHERE 
    S.schedule_date = '2025-11-05' -- Ngày mới được nhập
GROUP BY 
    R.name
ORDER BY 
    RoomName;

SELECT * FROM seats WHERE room_id = '1d0bd470-b5b7-4437-b201-6155d1be7bd0' and seat_type_id = 'eccf0df2-df4f-4eb7-a10b-29a3836e9b18'

SELECT 
    ST.name AS 'LoaiGhe',
    COUNT(S.seat_type_id) AS 'SoLuong'
FROM 
    seats S
JOIN 
    seat_types ST ON S.seat_type_id = ST.id
WHERE 
    S.room_id = '0754e6e5-e061-420b-a4ca-904e4742990b' 
GROUP BY 
    ST.name;


SELECT 
    position,
    CAST(SUBSTRING(position, 2, LEN(position) - 1) AS INT) AS SeatNumber
FROM 
    seats
WHERE 
    room_id = '2268d1fd-2448-45ae-80e5-8575de373c88' and seat_type_id = 'e0df25aa-3113-4494-a753-b55abf97652d'
ORDER BY 
    SeatNumber 
ASC

SELECT username, email, password FROM users WHERE email = 'lycustomer@gmail.com';

-- Truy vấn để lấy toàn bộ thông tin của chương trình khuyến mại
SELECT 
    p.id AS promotion_id,
    p.name AS promotion_name,
    p.description AS promotion_description,
    p.poster AS promotion_poster,
    p.discount_percent,
    p.discount_amount,
    p.start_date,
    p.end_date,
    p.active,
    p.is_deleted,
    pi.id AS promotion_item_id,
    pi.product_id,
    prod.name AS product_name,
    pi.film_id,
    f.name AS film_name,
    pi.seat_type_id,
    st.name AS seat_type_name,
    pi.note AS item_note,
    pr.id AS promotion_rule_id,
    pr.rule_type,
    pr.rule_value
FROM promotions p
LEFT JOIN promotion_items pi ON pi.promotion_id = p.id
LEFT JOIN products prod ON prod.id = pi.product_id
LEFT JOIN films f ON f.id = pi.film_id
LEFT JOIN seat_types st ON st.id = pi.seat_type_id
LEFT JOIN promotion_rules pr ON pr.promotion_id = p.id
WHERE p.is_deleted = 0
ORDER BY p.start_date DESC, p.name;


-- Truy vấn để lấy toàn bộ thông tin của giá vé
SELECT
    F.name AS TenPhim,
    ST.name AS LoaiGheApDung,
    SHT.start_time AS GioBatDauChieu,
    PT.day_type AS LoaiNgay,
    
    -- Sửa lỗi: Thay thế FORMAT tiền tệ bằng CONVERT(MONEY) để tránh lỗi CLR
    REPLACE(CONVERT(VARCHAR, CAST(PT.price AS MONEY), 1), '.00', '') + N' VNĐ' AS MucGiaApDung,
    
    -- Sửa lỗi: Thay thế FORMAT ngày tháng bằng CONVERT(VARCHAR, date, 103)
    CONVERT(VARCHAR, PT.start_date, 103) AS NgayBatDauApDung,
    ISNULL(CONVERT(VARCHAR, PT.end_date, 103), N'Vô thời hạn') AS NgayKetThucApDung,
    
    PT.id AS PriceRuleID,
    PT.film_id,
    PT.seat_type_id,
    PT.show_time_id

FROM price_tickets PT
JOIN films F ON PT.film_id = F.id
JOIN seat_types ST ON PT.seat_type_id = ST.id
JOIN show_times SHT ON PT.show_time_id = SHT.id
WHERE 
    PT.is_deleted = 0
ORDER BY 
    F.name, 
    PT.day_type, 
    SHT.start_time, 
    ST.name;

-- -- Lấy tất cả IDs của Phim và Suất Chiếu để sử dụng
-- DECLARE @FilmIDs TABLE (RowNum INT, film_id UNIQUEIDENTIFIER);
-- INSERT INTO @FilmIDs (RowNum, film_id)
-- SELECT ROW_NUMBER() OVER (ORDER BY name), id FROM films WHERE is_deleted = 0;

-- DECLARE @TimeIDs TABLE (RowNum INT, show_time_id UNIQUEIDENTIFIER);
-- INSERT INTO @TimeIDs (RowNum, show_time_id)
-- SELECT ROW_NUMBER() OVER (ORDER BY start_time), id FROM show_times WHERE is_deleted = 0;

-- -- Lấy IDs của các Phòng
-- DECLARE @Room1ID UNIQUEIDENTIFIER, @Room2ID UNIQUEIDENTIFIER, @Room3ID UNIQUEIDENTIFIER, @RoomVIP1ID UNIQUEIDENTIFIER;
-- SELECT @Room1ID = id FROM rooms WHERE name = N'Phòng 1';
-- SELECT @Room2ID = id FROM rooms WHERE name = N'Phòng 2';
-- SELECT @Room3ID = id FROM rooms WHERE name = N'Phòng 3';
-- SELECT @RoomVIP1ID = id FROM rooms WHERE name = N'Phòng VIP 1';

-- -- Ngày chiếu bắt đầu (chọn một ngày mới để tránh xung đột với dữ liệu cũ)
-- DECLARE @StartDate DATE = '2025-11-05';

-- DECLARE @BaseSchedules TABLE (
--     film_id UNIQUEIDENTIFIER NOT NULL,
--     show_time_id UNIQUEIDENTIFIER NOT NULL,
--     schedule_date DATE NOT NULL
-- );

-- -- Tạo 20 bản ghi (lặp lại lịch chiếu 18 lần và thêm 2 bản ghi đầu tiên vào cuối)
-- INSERT INTO @BaseSchedules (film_id, show_time_id, schedule_date)
-- SELECT
--     F.film_id,
--     T.show_time_id,
--     @StartDate
-- FROM
--     @FilmIDs F
-- JOIN
--     @TimeIDs T ON T.RowNum = (F.RowNum % 18) + 1; -- Ánh xạ 20 phim vào 18 suất chiếu, 2 phim cuối dùng lại suất đầu

-- INSERT INTO schedules (film_id, room_id, show_time_id, schedule_date)
-- SELECT BS.film_id, @Room1ID, BS.show_time_id, BS.schedule_date FROM @BaseSchedules BS
-- UNION ALL
-- SELECT BS.film_id, @Room2ID, BS.show_time_id, BS.schedule_date FROM @BaseSchedules BS
-- UNION ALL
-- SELECT BS.film_id, @Room3ID, BS.show_time_id, BS.schedule_date FROM @BaseSchedules BS
-- UNION ALL
-- SELECT BS.film_id, @RoomVIP1ID, BS.show_time_id, BS.schedule_date FROM @BaseSchedules BS;
-- GO