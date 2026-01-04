package com.example.backend.service;

import com.example.backend.dto.*;
import com.example.backend.entity.Role;
import com.example.backend.entity.UserProfile;
import com.example.backend.entity.UserRole;
import com.example.backend.entity.Users;
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StaffManageService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final FileStorageService fileStorageService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public List<UserProfileResponse> getAllStaff() {
        return userRepository.findAllStaff().stream()
                .map(this::toUserProfileResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ApiResponse createStaff(StaffCreateRequest request) throws IOException {
        if (userRepository.existsByUsername(request.username())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists");
        }

        String hashedPassword = passwordEncoder.encode(request.password());

        Users user = Users.builder()
                .username(request.username())
                .password(hashedPassword)
                .email(request.email())
                .enabled(true)
                .isDeleted(false)
                .build();

        userRepository.save(user);

        Role staffRole = roleRepository.findByName("STAFF");
        if (staffRole == null) {
            throw new EntityNotFoundException("Role STAFF not found");
        }
        UserRole userRole = UserRole.builder()
                .username(user.getUsername())
                .roleId(staffRole.getId())
                .user(user)
                .role(staffRole)
                .build();
        user.getUserRoles().add(userRole);

        String avatarPath = null;
        if (request.avatar() != null && !request.avatar().isEmpty()) {
            avatarPath = fileStorageService.saveFile(request.avatar());
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = null;
        try {
            birthday = sdf.parse(request.birthday());
        } catch (ParseException e) {
            throw new RuntimeException("Invalid birthday format");
        }

        UserProfile profile = UserProfile.builder()
                .username(user.getUsername())
                .user(user)
                .fullName(request.fullName())
                .gender(request.gender())
                .phone(request.phone())
                .address(request.address())
                .birthday(birthday)
                .avatarUrl(avatarPath)
                .build();
        user.setProfile(profile);

        userRepository.save(user);

        return new ApiResponse("success", "Thêm nhân viên mới thành công");
    }

    @Transactional
    public ApiResponse updateStaff(String username, StaffUpdateRequest request) throws IOException {
        Optional<Users> optionalUser = userRepository.findStaffByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException("Staff not found with username " + username);
        }
        Users user = optionalUser.get();

        UserProfile profile = user.getProfile();
        profile.setFullName(request.fullName());
        profile.setGender(request.gender());
        profile.setPhone(request.phone());
        profile.setAddress(request.address());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = null;
        try {
            birthday = sdf.parse(request.birthday());
        } catch (ParseException e) {
            throw new RuntimeException("Invalid birthday format");
        }
        profile.setBirthday(birthday);

        if (request.avatar() != null && !request.avatar().isEmpty()) {
            String avatarPath = fileStorageService.saveFile(request.avatar());
            profile.setAvatarUrl(avatarPath);
        }

        userRepository.save(user);

        return new ApiResponse("success", "Sửa thông tin nhân viên thành công");
    }

    @Transactional
    public ApiResponse deleteStaff(String username) {
        Optional<Users> optionalUser = userRepository.findStaffByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException("Staff not found with username " + username);
        }
        Users user = optionalUser.get();

        user.setIsDeleted(true);
        userRepository.save(user);

        return new ApiResponse("success", "Xoá nhân viên thành công");
    }

    private UserProfileResponse toUserProfileResponse(Users user) {
        UserProfile profile = user.getProfile();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String birthdayStr = profile.getBirthday() != null ? sdf.format(profile.getBirthday()) : null;

        return new UserProfileResponse(
                user.getUsername(),
                profile.getFullName(),
                profile.getGender(),
                profile.getPhone(),
                profile.getAddress(),
                birthdayStr,
                profile.getAvatarUrl(),
                user.getEmail()
        );
    }
}