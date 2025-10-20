package com.example.backend.service;

import com.example.backend.dto.RegisterResponse;
import com.example.backend.dto.RegisterRequest;
import com.example.backend.entity.Role;
import com.example.backend.entity.UserProfile;
import com.example.backend.entity.UserRole;
import com.example.backend.entity.Users;
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserProfileRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final UserProfileRepository profileRepo;
    private final UserRoleRepository userRoleRepo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    //otp đăng ký
    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        // 1️⃣ Kiểm tra trùng username
        if (userRepository.existsByUsername(request.username())) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại: " + request.username());
        }

        // 2️⃣ Mã hóa mật khẩu
        String encodedPassword = passwordEncoder.encode(request.password());

        // 3️⃣ Tạo đối tượng Users
        Users user = new Users();
        user.setUsername(request.username());
        user.setPassword(encodedPassword);
        user.setEmail(request.email()); // hoặc nhận email riêng nếu có
        user.setEnabled(true);

        // 4️⃣ Gán vai trò mặc định là CUSTOMER
        Role customerRole = roleRepository.findByName("CUSTOMER")
                .orElseThrow(() -> new RuntimeException("Không tìm thấy role CUSTOMER"));
        UserRole userRole = new UserRole();
        userRole.setUsername(request.username());
        userRole.setRoleId(customerRole.getId());
        userRole.setUser(user);
        userRole.setRole(customerRole);
        user.getUserRoles().add(userRole);

        // 5️⃣ Tạo hồ sơ cá nhân (UserProfile)
        UserProfile profile = new UserProfile();
        profile.setUser(user);
        profile.setFullName(request.fullName());
        profile.setGender(request.gender());
        profile.setPhone(request.phone());
        profile.setAddress(request.address());
        profile.setAvatarUrl("avatar.jpg");

        // Gộp ngày/tháng/năm thành Date
        LocalDate birthday = LocalDate.of(request.year(), request.month(), request.day());
        Date date = Date.from(birthday.atStartOfDay(ZoneId.systemDefault()).toInstant());
        profile.setBirthday(date);

        // Gán 2 chiều
        user.setProfile(profile);

        // 6️⃣ Lưu user (JPA tự cascade lưu cả UserRole và UserProfile)
        userRepository.save(user);

        return new RegisterResponse("status", "Register success.");
    }
}

