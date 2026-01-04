package com.example.backend.controller;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.StaffCreateRequest;
import com.example.backend.dto.StaffUpdateRequest;
import com.example.backend.dto.UserProfileResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

public interface StaffManageController {
    @GetMapping("")
    ResponseEntity<List<UserProfileResponse>> getAllStaff();

    @PostMapping("/create")
    ResponseEntity<ApiResponse> createStaff(@ModelAttribute StaffCreateRequest request) throws IOException;

    @PutMapping("/update/{username}")
    ResponseEntity<ApiResponse> updateStaff(@PathVariable String username, @ModelAttribute StaffUpdateRequest request) throws IOException;

    @DeleteMapping("/delete/{username}")
    ResponseEntity<ApiResponse> deleteStaff(@PathVariable String username);
}
