package com.example.backend.restapi;
//TODO: change password REST API and controller profile user

import com.example.backend.controller.UserProfileController;
import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.ChangePasswordRequest;
import com.example.backend.dto.UserProfileResponse;
import com.example.backend.dto.UserProfileUpdateRequest;
import com.example.backend.entity.UserProfile;
import com.example.backend.entity.Users;
import com.example.backend.repository.UserProfileRepository;
import com.example.backend.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserProfileAPI implements UserProfileController {


    private final UserProfileService userProfileService;


    @PutMapping("/{username}/changepassword")
    public ResponseEntity<ApiResponse> changePassword(String username,ChangePasswordRequest request) {

        return ResponseEntity.ok(userProfileService.changepassword(username,request));

    }

    @PutMapping("/{username}/updateprofile")
    public ResponseEntity<ApiResponse> updateProfile(String username, UserProfileUpdateRequest request) {
        return ResponseEntity.ok(userProfileService.updateProfile(username, request));
    }


    @GetMapping("/{username}")
    public ResponseEntity<Object> showProfile(String username) {
        UserProfileResponse response = userProfileService.getProfile(username);
        if (response.username() == null) {
            return ResponseEntity.ok(new ApiResponse("error", "User profile not found for id: " + username));
        }
        return ResponseEntity.ok(response);
    }


}
