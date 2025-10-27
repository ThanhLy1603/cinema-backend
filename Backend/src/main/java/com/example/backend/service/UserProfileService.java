package com.example.backend.service;
import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.ChangePasswordRequest;
import com.example.backend.dto.UserProfileResponse;
import com.example.backend.dto.UserProfileUpdateRequest;
import com.example.backend.entity.UserProfile;
import com.example.backend.entity.Users;
import com.example.backend.repository.UserProfileRepository;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserProfileResponse getProfile(String username) {
        Optional<UserProfile> userProfileOpt = userProfileRepository.findById(username);
        Optional<Users> userEmail = userRepository.findByUsername(username);
        if (userProfileOpt.isPresent()) {
            UserProfile userProfile = userProfileOpt.get();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return new UserProfileResponse(
                    userProfile.getUsername(),
                    userProfile.getFullName(),
                    userProfile.getGender(),
                    userProfile.getPhone(),
                    userProfile.getAddress(),
                    userProfile.getBirthday() != null ? dateFormat.format(userProfile.getBirthday()) : null,
                    userProfile.getAvatarUrl(),
                    userEmail.isPresent() ? userEmail.get().getEmail() : null);
        } else {
            return new UserProfileResponse(null, null, null, null, null, null, null,null);
        }
    }

    @Transactional
    public ApiResponse updateProfile(String username, UserProfileUpdateRequest request) {
        Optional<UserProfile> userProfileOpt = userProfileRepository.findById(username);
        if (userProfileOpt.isEmpty()) {
            return new ApiResponse("error", "Không tìm thấy hồ sơ người dùng: " + username);
        }
        UserProfile userProfile = userProfileOpt.get();
        userProfile.setUser(userProfile.getUser());
        userProfile.setFullName(request.firstName() + " " + request.lastName());
        userProfile.setGender(request.gender());
        userProfile.setPhone(request.phone());
        userProfile.setAddress(request.address());
        userProfile.setAvatarUrl("avatar.jpg");

        // Gộp ngày/tháng/năm thành Date
        LocalDate birthday = LocalDate.of(request.year(), request.month(), request.day());
        Date date = Date.from(birthday.atStartOfDay(ZoneId.systemDefault()).toInstant());
        userProfile.setBirthday(date);

        userProfileRepository.save(userProfile);
        return new ApiResponse("success", "Cập nhật hồ sơ thành công cho người dùng: " + username);
    }

    @Transactional
    public ApiResponse changepassword(String username,ChangePasswordRequest request) {
        Optional<Users> userOpt = userRepository.findById(username);
        if (userOpt.isEmpty()) {
            return new ApiResponse("error", "Không tìm thấy người dùng: " + username);
        }
        Users user = userOpt.get();
        // Kiểm tra mật khẩu cũ
        if (!passwordEncoder.matches(request.oldPassword(), user.getPassword())) {
            return new ApiResponse("error", "Mật khẩu cũ không đúng");
        }
        // Cập nhật mật khẩu mới
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
        return new ApiResponse("success", "Đổi mật khẩu thành công cho người dùng: " + username);
    }


}
