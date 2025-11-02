package com.example.backend.init;

import com.example.backend.entity.*;
import com.example.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Component
@RequiredArgsConstructor
public class DataInitialize implements EntityInitialize, CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final CategoryRepository categoryRepository;
    private final FilmRepository filmRepository;
    private final FilmCategoryRepository filmCategoryRepository;
    private final RoomRepository roomRepository;
    private final SeatRepository seatRepository;
    private final ShowTimeRepository showTimeRepository;
    private final SeatTypeRepository seatTypeRepository;
    private final ScheduleRepository scheduleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void initializeRoles() {
        if (roleRepository.count() == 0) {
            List<String> roleNames = Arrays.asList("ADMIN", "STAFF", "CUSTOMER");
            for (String name : roleNames) {
                Role role = new Role();
                role.setName(name);
                roleRepository.save(role);
            }
            System.out.println("✅ Đã khởi tạo bảng ROLE thành công!");
        } else {
            System.out.println("ℹ️ Bảng ROLE đã có dữ liệu, bỏ qua.");
        }
    }

    @Override
    @Transactional
    public void initializeUsersAndUserRoles() {
        if (userRepository.count() == 0) {
            String password = "$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli";

            Role adminRole = roleRepository.findByName("ADMIN").orElseThrow();
            Role staffRole = roleRepository.findByName("STAFF").orElseThrow();
            Role customerRole = roleRepository.findByName("CUSTOMER").orElseThrow();

            List<String> baseNames = Arrays.asList("Ly", "Thang", "Thien", "Quan", "Kiet", "My");
            List<Users> allUsers = new ArrayList<>();

            for (String base : baseNames) {
                Users admin = new Users(base + "Admin", password, base.toLowerCase() + "admin@gmail.com", true);
                admin.getUserRoles().add(new UserRole(admin.getUsername(), adminRole.getId(), admin, adminRole));

                Users staff = new Users(base + "Staff", password, base.toLowerCase() + "staff@gmail.com", true);
                staff.getUserRoles().add(new UserRole(staff.getUsername(), staffRole.getId(), staff, staffRole));

                Users customer = new Users(base + "Customer", password, base.toLowerCase() + "customer@gmail.com", true);
                customer.getUserRoles().add(new UserRole(customer.getUsername(), customerRole.getId(), customer, customerRole));

                allUsers.addAll(Arrays.asList(admin, staff, customer));
            }

            userRepository.saveAll(allUsers);
            System.out.println("✅ Đã khởi tạo bảng USERS và USER_ROLES thành công!");
        } else {
            System.out.println("ℹ️ Bảng USERS đã có dữ liệu, bỏ qua.");
        }
    }

    @Override
    @Transactional
    public void initializeUserProfiles() {
        if (userProfileRepository.count() == 0) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            List<Users> users = userRepository.findAll();

            for (Users user : users) {
                try {
                    if (user.getProfile() != null) continue;

                    UserProfile profile = new UserProfile();
                    profile.setUser(user);
                    user.setProfile(profile);
                    profile.setAvatarUrl("avatar.jpg");

                    String username = user.getUsername();
                    if (username.startsWith("Ly")) {
                        profile.setFullName("Nguyễn Thành Lý");
                        profile.setGender(true);
                        profile.setPhone("090100000" + getLastDigit(username));
                        profile.setAddress("Bình Thạnh, TP.HCM");
                        profile.setBirthday(sdf.parse("2000-03-16"));
                    } else if (username.startsWith("Thang")) {
                        profile.setFullName("Trương Cẩm Thắng");
                        profile.setGender(true);
                        profile.setPhone("090200000" + getLastDigit(username));
                        profile.setAddress("Quận 3, TP.HCM");
                        profile.setBirthday(sdf.parse("1997-08-21"));
                    } else if (username.startsWith("Thien")) {
                        profile.setFullName("Trần Lê Duy Thiện");
                        profile.setGender(true);
                        profile.setPhone("090300000" + getLastDigit(username));
                        profile.setAddress("Tân Bình, TP.HCM");
                        profile.setBirthday(sdf.parse("2002-08-25"));
                    } else if (username.startsWith("Quan")) {
                        profile.setFullName("Nguyễn Khắc Quân");
                        profile.setGender(true);
                        profile.setPhone("090400000" + getLastDigit(username));
                        profile.setAddress("Thủ Đức, TP.HCM");
                        profile.setBirthday(sdf.parse("1999-05-30"));
                    } else if (username.startsWith("Kiet")) {
                        profile.setFullName("Đinh Anh Kiệt");
                        profile.setGender(true);
                        profile.setPhone("090500000" + getLastDigit(username));
                        profile.setAddress("Gò Vấp, TP.HCM");
                        profile.setBirthday(sdf.parse("2002-02-11"));
                    } else if (username.startsWith("My")) {
                        profile.setFullName("Lê Hải My");
                        profile.setGender(false);
                        profile.setPhone("090600000" + getLastDigit(username));
                        profile.setAddress("Phú Nhuận, TP.HCM");
                        profile.setBirthday(sdf.parse("1997-01-13"));
                    }

                    userRepository.save(user); // cascade lưu profile

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            System.out.println("✅ Đã khởi tạo bảng USER_PROFILES thành công!");
        } else {
            System.out.println("ℹ️ Bảng USER_PROFILES đã có dữ liệu, bỏ qua.");
        }
    }

    @Override
    @Transactional
    public void initializeCategories() {
        if (categoryRepository.count() == 0) {
            List<String> categoryNames = Arrays.asList(
                    "Hành động",
                    "Khoa học viễn tưởng",
                    "Giả tưởng",
                    "Kịch tính",
                    "Tâm lý",
                    "Hài hước",
                    "Kinh dị",
                    "Lãng mạn",
                    "Phiêu lưu",
                    "Gia đình",
                    "Hoạt hình",
                    "Tội phạm",
                    "Bí ẩn",
                    "Tài liệu"
            );

            for (String name : categoryNames) {
                // Kiểm tra xem thể loại đã tồn tại trong DB chưa
                Category existingCategory = categoryRepository.findByName(name);

                if (existingCategory == null) {
                    // Nếu chưa tồn tại, tạo Entity mới và lưu vào DB
                    Category newCategory = new Category();
                    newCategory.setName(name);
                    newCategory.setDeleted(false); // is_deleted = 0

                    categoryRepository.save(newCategory);
                    // System.out.println("Đã thêm thể loại: " + name); // Tùy chọn để debug
                }
            }
            System.out.println("✅ Đã khởi tạo bảng CATEGORY thành công!");
        } else {
            System.out.println("ℹ️ Bảng CATEGORY đã có dữ liệu, bỏ qua.");
        }
    }

    @Override
    @Transactional
    public void initializeFilmsAndFilmCategories() {
        if (filmRepository.count() == 0) {
            System.out.println("🚀 Bắt đầu khởi tạo dữ liệu cho bảng FILMS...");

            List<Film> films = new ArrayList<>();

            // 1️⃣ Khởi tạo 20 phim
            Film f1 = new Film();
            f1.setName("5 Centimeters Per Second");
            f1.setCountry("Nhật Bản");
            f1.setDirector("Makoto Shinkai");
            f1.setActor("Kenji Mizuhashi, Yoshimi Kondo");
            f1.setDescription("Một câu chuyện tình yêu lãng mạn buồn bã được kể qua ba chương, theo chân Takaki Tono từ thời ấu thơ đến khi trưởng thành.");
            f1.setDuration(63);
            f1.setPoster("5cms_poster.webp");
            f1.setTrailer("5cms_trailer.mp4");
            f1.setReleaseDate(LocalDate.of(2025, 11, 1));
            f1.setStatus("active");
            f1.setDeleted(false);
            films.add(f1);

            Film f2 = new Film();
            f2.setName("Alice in Borderland 3");
            f2.setCountry("Nhật Bản");
            f2.setDirector("Shinsuke Sato");
            f2.setActor("Kento Yamazaki, Tao Tsuchiya");
            f2.setDescription("Sau khi trở về từ vùng biên giới, những người sống sót phải đối mặt với thực tế mới đầy bí ẩn và tàn khốc hơn.");
            f2.setDuration(120);
            f2.setPoster("Alice_in_border_land_3_poster.webp");
            f2.setTrailer("Alice_in_borderland_3_trailer.mp4");
            f2.setReleaseDate(LocalDate.of(2025, 12, 1));
            f2.setStatus("upcoming");
            f2.setDeleted(false);
            films.add(f2);

            Film f3 = new Film();
            f3.setName("Avatar 3");
            f3.setCountry("Mỹ");
            f3.setDirector("James Cameron");
            f3.setActor("Sam Worthington, Zoe Saldaña");
            f3.setDescription("Phần tiếp theo của siêu phẩm khoa học viễn tưởng này đưa khán giả đi sâu hơn vào thế giới Pandora huyền ảo.");
            f3.setDuration(192);
            f3.setPoster("Avatar_3_poster.jpg");
            f3.setTrailer("Avatar_3_trailer.mp4");
            f3.setReleaseDate(LocalDate.of(2025, 12, 19));
            f3.setStatus("upcoming");
            f3.setDeleted(false);
            films.add(f3);

            Film f4 = new Film();
            f4.setName("Bố Già 5 Siêu Đẳng Cấp");
            f4.setCountry("Hàn Quốc");
            f4.setDirector("Kang Hyoung Chul");
            f4.setActor("Yoo Ah In, Ra Mi Ran, Ahn Jae Hong, Park Jin Young, Kim Hee Won");
            f4.setDescription("Câu chuyện về một gia đình mafia cố gắng duy trì quyền lực trong thế giới ngầm đầy biến động.");
            f4.setDuration(135);
            f4.setPoster("Bo_5_sieu_dang_cap_poster.jpg");
            f4.setTrailer("Bo_5_sieu_dang_cap_trailer.mp4");
            f4.setReleaseDate(LocalDate.of(2025, 12, 15));
            f4.setStatus("upcoming");
            f4.setDeleted(false);
            films.add(f4);

            Film f5 = new Film();
            f5.setName("Búp Bê Sát Nhân");
            f5.setCountry("Mỹ");
            f5.setDirector("Gerard Johnstone");
            f5.setActor("Allison Williams, Violet McGraw");
            f5.setDescription("M3GAN là một robot AI giống búp bê, được lập trình để trở thành người bạn hoàn hảo cho trẻ em.");
            f5.setDuration(102);
            f5.setPoster("Bup_be_sat_nhan_poster.jpg");
            f5.setTrailer("Bup_be_sat_nhan_trailer.mp4");
            f5.setReleaseDate(LocalDate.of(2025, 11, 6));
            f5.setStatus("active");
            f5.setDeleted(false);
            films.add(f5);

            Film f6 = new Film();
            f6.setName("Conan Movie 20: The Darkest Nightmare");
            f6.setCountry("Nhật Bản");
            f6.setDirector("Kobun Shizuno");
            f6.setActor("Minami Takayama, Kappei Yamaguchi");
            f6.setDescription("Tổ chức Áo đen bí ẩn quay lại và gây ra hàng loạt vụ án phức tạp.");
            f6.setDuration(112);
            f6.setPoster("Conan_movie_20_poster.jpg");
            f6.setTrailer("Conan_movie_20_trailer.mp4");
            f6.setReleaseDate(LocalDate.of(2025, 11, 16));
            f6.setStatus("active");
            f6.setDeleted(false);
            films.add(f6);

            Film f7 = new Film();
            f7.setName("Deadpool 3");
            f7.setCountry("Mỹ");
            f7.setDirector("Shawn Levy");
            f7.setActor("Ryan Reynolds, Hugh Jackman");
            f7.setDescription("Deadpool bị kéo vào một nhiệm vụ đa vũ trụ cùng với Wolverine.");
            f7.setDuration(127);
            f7.setPoster("Deadpool_3_Poster.jpg");
            f7.setTrailer("Deadpool_3_trailer.mp4");
            f7.setReleaseDate(LocalDate.of(2025, 11, 26));
            f7.setStatus("active");
            f7.setDeleted(false);
            films.add(f7);

            Film f8 = new Film();
            f8.setName("Death Race 3: Inferno");
            f8.setCountry("Mỹ");
            f8.setDirector("Roel Reiné");
            f8.setActor("Luke Goss, Danny Trejo");
            f8.setDescription("Carl Lucas/Frankenstein đối mặt với thử thách cuối cùng tại sa mạc Kalahari khắc nghiệt.");
            f8.setDuration(105);
            f8.setPoster("Death_race_3_poster.jpg");
            f8.setTrailer("Death_race_3_trailer.mp4");
            f8.setReleaseDate(LocalDate.of(2025, 10, 22));
            f8.setStatus("inactive");
            f8.setDeleted(false);
            films.add(f8);

            Film f9 = new Film();
            f9.setName("Doraemon Movie 44: Nobita's Earth Symphony");
            f9.setCountry("Nhật Bản");
            f9.setDirector("Kazuaki Imai");
            f9.setActor("Wasabi Mizuta, Megumi Ohara");
            f9.setDescription("Doraemon và Nobita tham gia vào một cuộc phiêu lưu xuyên không gian và thời gian.");
            f9.setDuration(108);
            f9.setPoster("Doraemon_movie_44_poster.jpg");
            f9.setTrailer("Doraemon_movie_44_trailer.mp4");
            f9.setReleaseDate(LocalDate.of(2025, 12, 1));
            f9.setStatus("upcoming");
            f9.setDeleted(false);
            films.add(f9);

            Film f10 = new Film();
            f10.setName("Fast and Furious 7");
            f10.setCountry("Mỹ");
            f10.setDirector("James Wan");
            f10.setActor("Vin Diesel, Paul Walker, Jason Statham");
            f10.setDescription("Nhóm của Dom bị Deckard Shaw truy sát để trả thù.");
            f10.setDuration(137);
            f10.setPoster("Fast_and_furious_7_poster.jpg");
            f10.setTrailer("Fast_and_furious_7_trailer.mp4");
            f10.setReleaseDate(LocalDate.of(2025, 11, 3));
            f10.setStatus("active");
            f10.setDeleted(false);
            films.add(f10);

            Film f11 = new Film();
            f11.setName("Mad Max: Fury Road");
            f11.setCountry("Úc/Mỹ");
            f11.setDirector("George Miller");
            f11.setActor("Tom Hardy, Charlize Theron");
            f11.setDescription("Trong thế giới hậu tận thế, Max và Furiosa hợp tác để giải cứu các nô lệ.");
            f11.setDuration(120);
            f11.setPoster("Mad_max_poster.jpg");
            f11.setTrailer("Mad_max_trailer.mp4");
            f11.setReleaseDate(LocalDate.of(2025, 11, 15));
            f11.setStatus("active");
            f11.setDeleted(false);
            films.add(f11);

            Film f12 = new Film();
            f12.setName("Mắt Biếc");
            f12.setCountry("Việt Nam");
            f12.setDirector("Victor Vũ");
            f12.setActor("Trần Nghĩa, Trúc Anh, Trần Phong");
            f12.setDescription("Mối tình đơn phương đầy day dứt của Ngạn dành cho Hà Lan.");
            f12.setDuration(117);
            f12.setPoster("Mat_biec_poster.jpeg");
            f12.setTrailer("Mat_biec_trailer.mp4");
            f12.setReleaseDate(LocalDate.of(2025, 11, 20));
            f12.setStatus("active");
            f12.setDeleted(false);
            films.add(f12);

            Film f13 = new Film();
            f13.setName("Năm Mười Mười Lâm");
            f13.setCountry("Việt Nam");
            f13.setDirector("Tấn Hoàng Phong");
            f13.setActor("Trần Phong, Huỳnh Tú Uyên, Trần Vân Anh");
            f13.setDescription("Một bộ phim hài hước, tình cảm về nhóm bạn trẻ giữa lòng thành phố.");
            f13.setDuration(100);
            f13.setPoster("Nam_muoi_muoi_lam_poster.jpg");
            f13.setTrailer("Nam_muoi_muoi_lam_trailer.mp4");
            f13.setReleaseDate(LocalDate.of(2025, 12, 8));
            f13.setStatus("upcoming");
            f13.setDeleted(false);
            films.add(f13);

            Film f14 = new Film();
            f14.setName("Nhà Gia Tiên");
            f14.setCountry("Việt Nam");
            f14.setDirector("Huỳnh Lập");
            f14.setActor("Huỳnh Lập, Phương Mỹ Chi");
            f14.setDescription("Một bộ phim gia đình hài hước về một người cha che giấu tình hình tài chính.");
            f14.setDuration(95);
            f14.setPoster("Nha_gia_tien_poster.jpg");
            f14.setTrailer("Nha_gia_tien_trailer.mp4");
            f14.setReleaseDate(LocalDate.of(2025, 11, 20));
            f14.setStatus("active");
            f14.setDeleted(false);
            films.add(f14);

            Film f15 = new Film();
            f15.setName("Pacific Rim");
            f15.setCountry("Mỹ");
            f15.setDirector("Guillermo del Toro");
            f15.setActor("Charlie Hunnam, Idris Elba");
            f15.setDescription("Khi quái vật Kaiju trỗi dậy, con người phải chế tạo robot Jaeger để chiến đấu.");
            f15.setDuration(131);
            f15.setPoster("Pacific_rim_poster.webp");
            f15.setTrailer("Pacific_rim_trailer.mp4");
            f15.setReleaseDate(LocalDate.of(2025, 11, 12));
            f15.setStatus("active");
            f15.setDeleted(false);
            films.add(f15);

            Film f16 = new Film();
            f16.setName("Spirited Away");
            f16.setCountry("Nhật Bản");
            f16.setDirector("Hayao Miyazaki");
            f16.setActor("Rumi Hiiragi, Miyu Irino");
            f16.setDescription("Cô bé Chihiro lạc vào thế giới linh hồn và phải làm việc để cứu cha mẹ.");
            f16.setDuration(125);
            f16.setPoster("Spirited_away_poster.webp");
            f16.setTrailer("Spirited_away_trailer.mp4");
            f16.setReleaseDate(LocalDate.of(2025, 11, 20));
            f16.setStatus("active");
            f16.setDeleted(false);
            films.add(f16);

            Film f17 = new Film();
            f17.setName("Weathering with You");
            f17.setCountry("Nhật Bản");
            f17.setDirector("Makoto Shinkai");
            f17.setActor("Kotaro Daigo, Nana Mori");
            f17.setDescription("Hodaka gặp Hina – cô gái có khả năng điều khiển thời tiết ở Tokyo.");
            f17.setDuration(112);
            f17.setPoster("Tenki_no_ko_poster.jpg");
            f17.setTrailer("Tenki_no_ko_trailer.mp4");
            f17.setReleaseDate(LocalDate.of(2025, 11, 19));
            f17.setStatus("active");
            f17.setDeleted(false);
            films.add(f17);

            Film f18 = new Film();
            f18.setName("Tôi Thấy Hoa Vàng Trên Cỏ Xanh");
            f18.setCountry("Việt Nam");
            f18.setDirector("Victor Vũ");
            f18.setActor("Thịnh Vinh, Trọng Khang, Thanh Mỹ");
            f18.setDescription("Câu chuyện tuổi thơ của hai anh em Thiều và Tường tại làng quê nghèo.");
            f18.setDuration(103);
            f18.setPoster("Toi_thay_hoa_vang_tren_co_xanh_poster.jpg");
            f18.setTrailer("Toi_thay_hoa_vang_tren_co_xanh_trailer.mp4");
            f18.setReleaseDate(LocalDate.of(2025, 11, 2));
            f18.setStatus("active");
            f18.setDeleted(false);
            films.add(f18);

            Film f19 = new Film();
            f19.setName("Tử Chiến Trên Không");
            f19.setCountry("Mỹ");
            f19.setDirector("Lê Nhật Quang");
            f19.setActor("Thái Hoà, Kaity Nguyễn, Thanh Sơn, Xuân Phúc");
            f19.setDescription("Bình phải chiến đấu sinh tồn khi chuyến bay bị không tặc khống chế.");
            f19.setDuration(115);
            f19.setPoster("Tu_chien_tren_khong_poster.jpg");
            f19.setTrailer("Tu_chien_tren_khong_trailer.mp4");
            f19.setReleaseDate(LocalDate.of(2025, 10, 6));
            f19.setStatus("active");
            f19.setDeleted(false);
            films.add(f19);

            Film f20 = new Film();
            f20.setName("Your Name");
            f20.setCountry("Nhật Bản");
            f20.setDirector("Makoto Shinkai");
            f20.setActor("Ryunosuke Kamiki, Mone Kamishiraishi");
            f20.setDescription("Hai học sinh bị hoán đổi cơ thể và tìm cách gặp nhau trước khi thảm họa xảy ra.");
            f20.setDuration(107);
            f20.setPoster("Your_name_poster.jpeg");
            f20.setTrailer("Your_name_trailer.mp4");
            f20.setReleaseDate(LocalDate.of(2016, 8, 26));
            f20.setStatus("active");
            f20.setDeleted(false);
            films.add(f20);

            // Lưu phim
            filmRepository.saveAll(films);
            System.out.println("✅ Đã thêm 20 phim thành công!");

            // 2️⃣ Mapping phim - thể loại
            Map<String, List<String>> mapping = Map.ofEntries(
                    Map.entry("5 Centimeters Per Second", List.of("Lãng mạn", "Kịch tính")),
                    Map.entry("Alice in Borderland 3", List.of("Hành động", "Khoa học viễn tưởng", "Kịch tính")),
                    Map.entry("Avatar 3", List.of("Khoa học viễn tưởng", "Hành động", "Phiêu lưu")),
                    Map.entry("Bố Già 5 Siêu Đẳng Cấp", List.of("Hành động", "Hài hước")),
                    Map.entry("Búp Bê Sát Nhân", List.of("Kinh dị", "Hành động")),
                    Map.entry("Conan Movie 20: The Darkest Nightmare", List.of("Hoạt hình", "Tội phạm", "Hành động")),
                    Map.entry("Deadpool 3", List.of("Hành động", "Hài hước", "Khoa học viễn tưởng")),
                    Map.entry("Death Race 3: Inferno", List.of("Hành động", "Khoa học viễn tưởng")),
                    Map.entry("Doraemon Movie 44: Nobita's Earth Symphony", List.of("Hoạt hình", "Gia đình", "Phiêu lưu")),
                    Map.entry("Fast and Furious 7", List.of("Hành động", "Phiêu lưu")),
                    Map.entry("Mad Max: Fury Road", List.of("Hành động", "Phiêu lưu")),
                    Map.entry("Mắt Biếc", List.of("Lãng mạn", "Kịch tính")),
                    Map.entry("Năm Mười Mười Lâm", List.of("Hài hước", "Gia đình")),
                    Map.entry("Nhà Gia Tiên", List.of("Kịch tính", "Tâm lý")),
                    Map.entry("Pacific Rim", List.of("Khoa học viễn tưởng", "Hành động")),
                    Map.entry("Spirited Away", List.of("Hoạt hình", "Giả tưởng", "Phiêu lưu")),
                    Map.entry("Weathering with You", List.of("Hoạt hình", "Lãng mạn", "Giả tưởng")),
                    Map.entry("Tôi Thấy Hoa Vàng Trên Cỏ Xanh", List.of("Gia đình", "Kịch tính", "Lãng mạn")),
                    Map.entry("Tử Chiến Trên Không", List.of("Hành động", "Kịch tính")),
                    Map.entry("Your Name", List.of("Lãng mạn", "Giả tưởng"))
            );

            for (Film film : films) {
                List<String> categoryNames = mapping.getOrDefault(film.getName(), List.of());
                for (String catName : categoryNames) {
                    Category category = categoryRepository.findByName(catName);
                    if (category != null) {
                        FilmCategory fc = new FilmCategory();
                        fc.setFilm(film);
                        fc.setCategory(category);
                        filmCategoryRepository.save(fc);
                    }
                }
            }

            System.out.println("✅ Đã khởi tạo bảng FILM và FILM_CATEGORY thành công!");
        } else {
            System.out.println("ℹ️ Bảng FILM và FILM_CATEGORY đã có dữ liệu, bỏ qua khởi tạo.");
        }
    }

    @Override
    @Transactional
    public void initializeSeatType() {
        if (seatTypeRepository.count() == 0) {
            List<String> seatTypeNames = Arrays.asList("Ghế Thường", "Ghế VIP", "Ghế Couple");
            for (String name : seatTypeNames) {
                SeatType seatType = new SeatType();
                seatType.setName(name);
                seatType.setDeleted(false);

                seatTypeRepository.save(seatType);
            }
            System.out.println("✅ Đã khởi tạo bảng SEAT_TYPE thành công!");
        } else {
            System.out.println("ℹ️ Bảng SEAT_TYPE đã có dữ liệu, bỏ qua.");
        }
    }

    @Override
    @Transactional
    public void initializeRooms() {
        if (roomRepository.count() == 0) {
            List<String> roomNames = Arrays.asList("Phòng 1", "Phòng 2", "Phòng 3", "Phòng VIP 1");
            for (String name : roomNames) {
                Room room = new Room();
                room.setName(name);
                room.setDeleted(false);

                roomRepository.save(room);
            }
            System.out.println("✅ Đã khởi tạo bảng ROOM thành công!");
        } else {
            System.out.println("ℹ️ Bảng ROOM đã có dữ liệu, bỏ qua.");
        }
    }

    @Override
    @Transactional
    public void initializeShowTimes() {
        if (showTimeRepository.count() == 0) {

            List<String> showTimeStrings = Arrays.asList(
                    "07:00", "08:00", "09:00", "10:00",
                    "11:00", "12:00", "13:00", "14:00",
                    "15:00", "16:00", "17:00", "18:00",
                    "19:00", "20:00", "21:00", "22:00",
                    "23:00", "00:00"
            );

            for (String timeStr : showTimeStrings) {
                ShowTime showTime = new ShowTime();

                // Chuyển đổi chuỗi "HH:mm" thành LocalTime
                showTime.setStartTime(LocalTime.parse(timeStr));

                // Đặt isDeleted (nếu chưa gán mặc định trong Entity)
                showTime.setIsDeleted(false);

                showTimeRepository.save(showTime);
            }

            System.out.println("✅ Đã khởi tạo bảng SHOW_TIMES thành công!");
        } else {
            System.out.println("ℹ️ Bảng SHOW_TIMES đã có dữ liệu, bỏ qua.");
        }
    }

    @Override
    @Transactional
    public void initializeSeats() {

        if (seatRepository.count() > 0) {
            System.out.println("ℹ️ Bảng SEATS đã có dữ liệu, bỏ qua khởi tạo.");
            return;
        }

        List<Room> rooms = roomRepository.findByIsDeletedFalse();
        if (rooms.isEmpty()) {
            System.out.println("⚠️ Không tìm thấy phòng. Hãy thêm Room trước!");
            return;
        }

        // Lấy seat types
        SeatType thuong = seatTypeRepository.findByName("Ghế Thường");
        SeatType vip = seatTypeRepository.findByName("Ghế VIP");
        SeatType couple = seatTypeRepository.findByName("Ghế Couple");

        if (thuong == null || vip == null || couple == null) {
            System.out.println("❌ Chưa có dữ liệu bảng seat_types (Ghế Thường, VIP, Couple)");
            return;
        }

        // Total seats: 1 → 156
        int totalSeats = 156;

        for (Room room : rooms) {

            for (int number = 1; number <= totalSeats; number++) {

                // Determine row letter
                String rowLetter =
                        (number <= 15)  ? "A" :
                        (number <= 30)  ? "B" :
                        (number <= 45)  ? "C" :
                        (number <= 60)  ? "D" :
                        (number <= 75)  ? "E" :
                        (number <= 90)  ? "F" :
                        (number <= 105) ? "G" :
                        (number <= 120) ? "H" :
                        (number <= 135) ? "I" :
                        (number <= 150) ? "J" :
                        "K";

                // Determine SeatType
                SeatType seatType;

                // Ghế VIP
                if ((number >= 49 && number <= 57) ||
                        (number >= 64 && number <= 72) ||
                        (number >= 79 && number <= 87) ||
                        (number >= 94 && number <= 102)) {
                    seatType = vip;
                }
                // Ghế Couple
                else if (number >= 151 && number <= 156) {
                    seatType = couple;
                }
                // Ghế Thường
                else {
                    seatType = thuong;
                }

                // Position like A1, B25, K156
                String position = rowLetter + number;

                Seat seat = new Seat();
                seat.setRoom(room);
                seat.setSeatType(seatType);
                seat.setPosition(position);
                seat.setDeleted(false);

                seatRepository.save(seat);
            }
        }

        System.out.println("✅ Đã khởi tạo bảng SEATS thành công!");
    }

    @Override
    @Transactional
    public void initializeSchedules() {
        if (scheduleRepository.count() == 0) {

            LocalDate startDate = LocalDate.of(2025, 11, 5);

            List<Film> films = filmRepository.findByIsDeletedFalse();
            List<ShowTime> showTimes = showTimeRepository.findByIsDeletedFalseOrderByStartTimeAsc();
            List<Room> rooms = roomRepository.findByIsDeletedFalseOrderByNameAsc();

            if (films.isEmpty() || showTimes.isEmpty() || rooms.isEmpty()) {
                System.out.println("❌ Không thể khởi tạo SCHEDULES vì thiếu dữ liệu (Films/ShowTimes/Rooms)");
                return;
            }

            List<Schedule> schedules = new ArrayList<>();

            for (Film film : films) {
                for (ShowTime showTime : showTimes) {
                    for (Room room : rooms) {

                        // kiểm tra trùng (unique constraint tránh lỗi)
                        boolean exists = scheduleRepository
                                .existsByRoomAndFilmAndShowTimeAndScheduleDate(
                                        room, film, showTime, startDate
                                );

                        if (!exists) {
                            Schedule schedule = Schedule.builder()
                                    .film(film)
                                    .room(room)
                                    .showTime(showTime)
                                    .scheduleDate(startDate)
                                    .isDeleted(false)
                                    .build();

                            schedules.add(schedule);
                        }
                    }
                }
            }

            scheduleRepository.saveAll(schedules);

            System.out.println("✅ Đã khởi tạo bảng SCHEDULES thành công! Tổng: "
                    + schedules.size() + " lịch chiếu.");

        } else {
            System.out.println("ℹ️ Bảng SCHEDULES đã có dữ liệu, bỏ qua.");
        }
    }



    private int getLastDigit(String username) {
        if (username.endsWith("Admin")) return 1;
        if (username.endsWith("Staff")) return 2;
        if (username.endsWith("Customer")) return 3;
        return 0;
    }

    @Override
    @Transactional
    public void run(String... args) {
        initializeRoles();
        initializeUsersAndUserRoles();
        initializeUserProfiles();
        initializeCategories();
        initializeFilmsAndFilmCategories();
        initializeSeatType();
        initializeRooms();
        initializeShowTimes();
        initializeSeats();
        initializeSchedules();
    }
}
