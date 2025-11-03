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

-- Lấy tất cả IDs của Phim và Suất Chiếu để sử dụng
DECLARE @FilmIDs TABLE (RowNum INT, film_id UNIQUEIDENTIFIER);
INSERT INTO @FilmIDs (RowNum, film_id)
SELECT ROW_NUMBER() OVER (ORDER BY name), id FROM films WHERE is_deleted = 0;

DECLARE @TimeIDs TABLE (RowNum INT, show_time_id UNIQUEIDENTIFIER);
INSERT INTO @TimeIDs (RowNum, show_time_id)
SELECT ROW_NUMBER() OVER (ORDER BY start_time), id FROM show_times WHERE is_deleted = 0;

-- Lấy IDs của các Phòng
DECLARE @Room1ID UNIQUEIDENTIFIER, @Room2ID UNIQUEIDENTIFIER, @Room3ID UNIQUEIDENTIFIER, @RoomVIP1ID UNIQUEIDENTIFIER;
SELECT @Room1ID = id FROM rooms WHERE name = N'Phòng 1';
SELECT @Room2ID = id FROM rooms WHERE name = N'Phòng 2';
SELECT @Room3ID = id FROM rooms WHERE name = N'Phòng 3';
SELECT @RoomVIP1ID = id FROM rooms WHERE name = N'Phòng VIP 1';

-- Ngày chiếu bắt đầu (chọn một ngày mới để tránh xung đột với dữ liệu cũ)
DECLARE @StartDate DATE = '2025-11-05';

DECLARE @BaseSchedules TABLE (
    film_id UNIQUEIDENTIFIER NOT NULL,
    show_time_id UNIQUEIDENTIFIER NOT NULL,
    schedule_date DATE NOT NULL
);

-- Tạo 20 bản ghi (lặp lại lịch chiếu 18 lần và thêm 2 bản ghi đầu tiên vào cuối)
INSERT INTO @BaseSchedules (film_id, show_time_id, schedule_date)
SELECT
    F.film_id,
    T.show_time_id,
    @StartDate
FROM
    @FilmIDs F
JOIN
    @TimeIDs T ON T.RowNum = (F.RowNum % 18) + 1; -- Ánh xạ 20 phim vào 18 suất chiếu, 2 phim cuối dùng lại suất đầu

INSERT INTO schedules (film_id, room_id, show_time_id, schedule_date)
SELECT BS.film_id, @Room1ID, BS.show_time_id, BS.schedule_date FROM @BaseSchedules BS
UNION ALL
SELECT BS.film_id, @Room2ID, BS.show_time_id, BS.schedule_date FROM @BaseSchedules BS
UNION ALL
SELECT BS.film_id, @Room3ID, BS.show_time_id, BS.schedule_date FROM @BaseSchedules BS
UNION ALL
SELECT BS.film_id, @RoomVIP1ID, BS.show_time_id, BS.schedule_date FROM @BaseSchedules BS;
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


SELECT * FROM user_roles
SELECT * FROM users
SELECT * FROM rolesW
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
    S.schedule_date = '2025-11-05' 
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
    S.room_id = '2268d1fd-2448-45ae-80e5-8575de373c88' 
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