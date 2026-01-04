package com.example.backend.controller;

import com.example.backend.dto.response.ApiResponse;
import com.example.backend.dto.request.ChangePasswordRequest;
import com.example.backend.dto.request.UserProfileUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserProfileController {

    public ResponseEntity<Object> showProfile(@PathVariable String id);
    public ResponseEntity<ApiResponse> changePassword(@PathVariable String username,@RequestBody ChangePasswordRequest request);
    public ResponseEntity<ApiResponse> updateProfile(@PathVariable String username,@RequestBody UserProfileUpdateRequest request);
}
