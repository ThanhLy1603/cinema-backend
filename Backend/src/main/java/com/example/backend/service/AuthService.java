package com.example.backend.service;

import com.example.backend.dto.request.LoginRequest;
import com.example.backend.dto.request.ResetPasswordRequest;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.dto.response.LoginResponse;
import com.example.backend.dto.response.RegisterRequest;
import com.example.backend.entity.Role;
import com.example.backend.entity.UserProfile;
import com.example.backend.entity.UserRole;
import com.example.backend.entity.Users;
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomerDetailsService customerDetailsService;
    private final JwtService jwtService;

    @Transactional
    public Object login(LoginRequest request) {
        try {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(request.username(), request.password());

            authenticationManager.authenticate(authToken);

            UserDetails userDetails = customerDetailsService.loadUserByUsername(request.username());
            System.out.println("User details: " + userDetails.getUsername() + ", " +
                    userDetails.getAuthorities());

            String token = jwtService.generateToken(userDetails);

            return new LoginResponse(token);

        } catch (DisabledException e) {
            return new ApiResponse("error", "Tài khoản đã bị vô hiệu hóa");
        } catch (BadCredentialsException e) {
            return new ApiResponse("error", "Sai tên đăng nhập hoặc mật khẩu");
        } catch (Exception e) {
            return new ApiResponse("error", "Lỗi khi kết nối đến máy chủ");
        }
    }

    //otp đăng ký
    @Transactional
    public ApiResponse register(RegisterRequest request) {
        try {
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
            System.out.println(" Đăng ký thành công:" + user.getUsername());
            return new ApiResponse("status", "Đăng ký thành công.");
        } catch (Exception e) {
            // Rollback tự động do @Transactional, chỉ cần log để theo dõi
            System.err.println("Lỗi khi đăng ký user {}: {}" + request.username() + e.getMessage());
            return new ApiResponse("error", "Đăng ký thất bại: " + e.getMessage());
        }
    }

    @Transactional
    public ApiResponse resetPassword(ResetPasswordRequest request) {
        Users user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng: " + request.email()));
        String encodedPassword = passwordEncoder.encode(request.newPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return new ApiResponse("success","Đặt lại mật khẩu thành công cho tài khoản " + request.email());
    }

}

