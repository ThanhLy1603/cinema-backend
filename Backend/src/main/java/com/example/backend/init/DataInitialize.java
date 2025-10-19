//package com.example.backend.init;
//
//import com.example.backend.entity.Role;
//import com.example.backend.entity.UserProfile;
//import com.example.backend.entity.Users;
//import com.example.backend.repository.RoleRepository;
//import com.example.backend.repository.UserProfileRepository;
//import com.example.backend.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//@Component
//public class DataInitialize implements EntityInitialize, CommandLineRunner {
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private UserProfileRepository userProfileRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public void initializeRoles() {
//        if (roleRepository.count() == 0) {
//            List<String> roleNames = Arrays.asList("ADMIN", "STAFF", "CUSTOMER");
//            for (String name : roleNames) {
//                Role role = new Role();
//                role.setName(name);
//                roleRepository.save(role);
//            }
//            System.out.println("✅ Đã khởi tạo bảng ROLE thành công!");
//        } else {
//            System.out.println("ℹ️ Bảng ROLE đã có dữ liệu, bỏ qua.");
//        }
//    }
//
//    @Override
//    public void initializeUsersAndUserRoles() {
//        if (userRepository.count() == 0) {
//            // Sử dụng đúng hash password trong script SQL
//            String password = "$2a$10$bbvAcEp3Bcov0irbT24Xnuef9YHpSZBYnuXktTQ7S.nnYOvS7ABli";
//
//            Role adminRole = roleRepository.findByName("ADMIN").orElseThrow();
//            Role staffRole = roleRepository.findByName("STAFF").orElseThrow();
//            Role customerRole = roleRepository.findByName("CUSTOMER").orElseThrow();
//
//            List<String> baseNames = Arrays.asList("Ly", "Thang", "Thien", "Quan", "Kiet", "My");
//
//            List<Users> allUsers = new ArrayList<>();
//
//            for (String base : baseNames) {
//                Users admin = new Users(base + "Admin", password, base.toLowerCase() + "admin@gmail.com", true);
//                admin.setRoles(Set.of(adminRole));
//
//                Users staff = new Users(base + "Staff", password, base.toLowerCase() + "staff@gmail.com", true);
//                staff.setRoles(Set.of(staffRole));
//
//                Users customer = new Users(base + "Customer", password, base.toLowerCase() + "customer@gmail.com", true);
//                customer.setRoles(Set.of(customerRole));
//
//                allUsers.addAll(Arrays.asList(admin, staff, customer));
//            }
//
//            userRepository.saveAll(allUsers);
//            System.out.println("✅ Đã khởi tạo bảng USERS và USER_ROLES thành công!");
//        } else {
//            System.out.println("ℹ️ Bảng USERS đã có dữ liệu, bỏ qua.");
//        }
//    }
//
//    @Override
//    public void initializeUserProfiles() {
//        if (userProfileRepository.count() > 0) {
//            System.out.println("ℹ️ Bảng USER_PROFILES đã có dữ liệu, bỏ qua.");
//            return;
//        }
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        List<Users> users = userRepository.findAll(); // users are managed when fetched in tx (merge if needed)
//
//        for (Users user : users) {
//            try {
//                // nếu user đã có profile (từ DB), bỏ qua
//                if (user.getProfile() != null) {
//                    continue;
//                }
//
//                UserProfile profile = new UserProfile();
//                // KHÔNG set profile.setUsername(...) vì @MapsId sẽ dùng username của user
//                profile.setAvatarUrl("avatar.jpg");
//
//                String username = user.getUsername();
//                if (username.startsWith("Ly")) {
//                    profile.setFullName("Nguyễn Thành Lý");
//                    profile.setGender(true);
//                    profile.setPhone("090100000" + getLastDigit(username));
//                    profile.setAddress("Bình Thạnh, TP.HCM");
//                    profile.setBirthday(sdf.parse("2000-03-16"));
//                } else if (username.startsWith("Thang")) {
//                    profile.setFullName("Trương Cẩm Thắng");
//                    profile.setGender(true);
//                    profile.setPhone("090200000" + getLastDigit(username));
//                    profile.setAddress("Quận 3, TP.HCM");
//                    profile.setBirthday(sdf.parse("1997-08-21"));
//                } else if (username.startsWith("Thien")) {
//                    profile.setFullName("Trần Lê Duy Thiện");
//                    profile.setGender(true);
//                    profile.setPhone("090300000" + getLastDigit(username));
//                    profile.setAddress("Tân Bình, TP.HCM");
//                    profile.setBirthday(sdf.parse("2002-08-25"));
//                } else if (username.startsWith("Quan")) {
//                    profile.setFullName("Nguyễn Khắc Quân");
//                    profile.setGender(true);
//                    profile.setPhone("090400000" + getLastDigit(username));
//                    profile.setAddress("Thủ Đức, TP.HCM");
//                    profile.setBirthday(sdf.parse("1999-05-30"));
//                } else if (username.startsWith("Kiet")) {
//                    profile.setFullName("Đinh Anh Kiệt");
//                    profile.setGender(true);
//                    profile.setPhone("090500000" + getLastDigit(username));
//                    profile.setAddress("Gò Vấp, TP.HCM");
//                    profile.setBirthday(sdf.parse("2002-02-11"));
//                } else if (username.startsWith("My")) {
//                    profile.setFullName("Lê Hải My");
//                    profile.setGender(false);
//                    profile.setPhone("090600000" + getLastDigit(username));
//                    profile.setAddress("Phú Nhuận, TP.HCM");
//                    profile.setBirthday(sdf.parse("1997-01-13"));
//                }
//
//                // quan trọng: set 2 chiều
//                profile.setUser(user);
//                user.setProfile(profile);
//
//                // lưu user — cascade sẽ lưu profile
//                userRepository.save(user);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        System.out.println("✅ Đã khởi tạo bảng USER_PROFILES thành công!");
//    }
//
//    private int getLastDigit(String username) {
//        if (username.endsWith("Admin")) return 1;
//        if (username.endsWith("Staff")) return 2;
//        if (username.endsWith("Customer")) return 3;
//        return 0;
//    }
//
//    @Override
//    public void run(String... args) {
//        initializeRoles();
//        initializeUsersAndUserRoles();
//        initializeUserProfiles();
//    }
//}
