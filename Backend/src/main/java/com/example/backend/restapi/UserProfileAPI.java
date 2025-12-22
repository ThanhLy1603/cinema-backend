package com.example.backend.restapi;

import com.example.backend.controller.UserProfileController;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.dto.request.ChangePasswordRequest;
import com.example.backend.dto.response.UserProfileResponse;
import com.example.backend.dto.request.UserProfileUpdateRequest;
import com.example.backend.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserProfileAPI implements UserProfileController {

    private final UserProfileService userProfileService;

    @PutMapping("/{username}/change-password")
    public ResponseEntity<ApiResponse> changePassword(@PathVariable String username, @RequestBody ChangePasswordRequest request) {
        return ResponseEntity.ok(userProfileService.changePassword(username,request));
    }

    @PutMapping("/{username}/update-profile")
    public ResponseEntity<ApiResponse> updateProfile(@PathVariable String username, @ModelAttribute UserProfileUpdateRequest request) {
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
