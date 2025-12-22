package com.example.backend.service;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.dto.request.ChangePasswordRequest;
import com.example.backend.dto.response.UserProfileResponse;
import com.example.backend.dto.request.UserProfileUpdateRequest;
import com.example.backend.entity.UserProfile;
import com.example.backend.entity.Users;
import com.example.backend.repository.UserProfileRepository;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
    private final FileStorageService fileStorageService;

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
            return new ApiResponse("error", "Không tìm thấy hồ sơ người dùng");
        }

        UserProfile userProfile = userProfileOpt.get();

            userProfile.setFullName(request.firstName() + " " + request.lastName());
            userProfile.setGender(request.gender());
            userProfile.setPhone(request.phone());
            userProfile.setAddress(request.address());

            LocalDate birthday = LocalDate.of(request.year(), request.month(), request.day());
            Date date = Date.from(birthday.atStartOfDay(ZoneId.systemDefault()).toInstant());
            userProfile.setBirthday(date);

        MultipartFile avatar = request.avatarUrl();

        if (avatar != null && !avatar.isEmpty()) {
            try {
                String savedPath = fileStorageService.saveFile(avatar);
                System.out.println("saved path: " + savedPath);
                userProfile.setAvatarUrl(savedPath);
            } catch (IOException e) {
                return new ApiResponse("error", "Không thể lưu hình ảnh: " + avatar.getOriginalFilename());
            }
        }

        userProfileRepository.save(userProfile);
        return new ApiResponse("success", "Cập nhật hồ sơ thông tin cá nhân thành công");
    }

    @Transactional
    public ApiResponse changePassword(String username,ChangePasswordRequest request) {
        Optional<Users> userOpt = userRepository.findById(username);

        if (userOpt.isPresent()) {
            Users user = userOpt.get();
            System.out.println("Username: " + user.getUsername());
        } else {
            System.out.println("Không tìm thấy người dùng " + username);
        }

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
