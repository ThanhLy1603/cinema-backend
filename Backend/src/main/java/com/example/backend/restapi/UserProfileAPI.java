package com.example.backend.restapi;
//TODO: change password REST API and controller profile user

import com.example.backend.controller.UserProfileController;
import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.UserProfileResponse;
import com.example.backend.entity.UserProfile;
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

    @GetMapping("/{username}")
    public ResponseEntity<Object> showProfile(@PathVariable String username) {
        UserProfileResponse response = userProfileService.getProfile(username);
        if (response.username() == null) {
            return ResponseEntity.ok(new ApiResponse("error", "User profile not found for id: " + username));
        }
        return ResponseEntity.ok(response);
    }

//    @PostMapping("/change-password/{email}")
//    public String forgotPasswordSubmit(@PathVariable String email, @RequestBody String newPassword) {
//        users = userRe.findByUsername(email).orElse(null);
//        if (users == null) {
//            return "Email not found: " + email;
//        }
//
//        userRe.setPassword(newPassword);
//        userRe.save(users);
//
//        return "Forgot Password submitted for email: " + email;
//    }

}
