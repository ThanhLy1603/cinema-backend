//package com.example.backend.test;
//
//import com.example.backend.entity.*;
//import com.example.backend.repository.RoleRepository;
//import com.example.backend.repository.UserProfileRepository;
//import com.example.backend.repository.UserRepository;
//import com.example.backend.repository.UserRoleRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.text.SimpleDateFormat;
//
//@Component
//public class TestInsert implements CommandLineRunner {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Autowired
//    private UserRoleRepository userRoleRepository;
//
//    @Autowired
//    private UserProfileRepository userProfileRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    @Transactional
//    public void run(String... args) throws Exception {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//        // ===== 1️⃣ Lấy hoặc tạo role =====
//        Role role = roleRepository.findByName("STAFF").orElseGet(() -> {
//            Role newRole = new Role();
//            newRole.setName("STAFF");
//            return roleRepository.save(newRole);
//        });
//
//        // ===== 2️⃣ Tạo user =====
//        Users user = new Users();
//        user.setUsername("LyStaff1");
//        user.setPassword(passwordEncoder.encode("Datn12345"));
//        user.setEmail("lystaff1@gmail.com");
//        user.setEnabled(true);
//
//        // ===== 3️⃣ Tạo user profile (1-1) =====
//        UserProfile profile = new UserProfile();
//        profile.setUser(user); // gán 1-1
//        profile.setFullName("Nguyễn Thành Lý");
//        profile.setGender(true);
//        profile.setPhone("0908791223");
//        profile.setAddress("Long An");
//        profile.setBirthday(sdf.parse("2000-03-16"));
//        profile.setAvatarUrl("avatar.jpg");
//
//        user.setProfile(profile); // gán 2 chiều
//
//        // Lưu user (cascade sẽ lưu profile)
//        userRepository.save(user);
//
//        // ===== 4️⃣ Tạo UserRole (nhiều-nhiều) =====
//        UserRole userRole = UserRole.builder()
//                .username(user.getUsername())
//                .roleId(role.getId())
//                .user(user)
//                .role(role)
//                .build();
//
//        // Gán 1 chiều từ user
//        user.getUserRoles().add(userRole);
//
//        // Lưu UserRole
//        userRoleRepository.save(userRole);
//
//        // ===== 5️⃣ In ra kiểm tra =====
//        System.out.println("✅ Inserted successfully:");
//        System.out.println("- User: " + user.getUsername());
//        System.out.println("- Role: " + role.getName());
//        System.out.println("- Profile: " + profile.getFullName());
//        System.out.println("- UserRole: " + userRole.getUsername() + " -> " + userRole.getRole().getName());
//    }
//}
