package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
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
    public ApiResponse register(RegisterRequest req) {
        if (userRepo.existsByUsername(req.username()))
            return new ApiResponse("error", "Email đã tồn tại!");

        Users user = Users.builder()
                .username(req.username())
                .email(req.username())
                .password(encoder.encode(req.password()))
                .enabled(true)
                .build();
        userRepo.save(user);

        Role role = roleRepo.findByName("CUSTOMER")
                .orElseThrow(() -> new RuntimeException("Role CUSTOMER chưa có trong DB"));
        userRoleRepo.save(UserRole.builder()
                .username(user.getUsername())
                .roleId(role.getId())
                .build());

        Calendar cal = Calendar.getInstance();
        cal.set(req.nam(), req.thang() - 1, req.ngay());
        UserProfile profile = UserProfile.builder()
                .username(user.getUsername())
                .user(user)
                .fullName(req.fullName())
                .gender(req.gender())
                .phone(req.phone())
                .address(req.address())
                .birthday(cal.getTime())
                .build();
        profileRepo.save(profile);

        return new ApiResponse("success", "Đăng ký tài khoản thành công!");
    }
}

