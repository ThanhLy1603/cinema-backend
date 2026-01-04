package com.example.backend.restapi;

import com.example.backend.controller.StaffManageController;
import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.StaffCreateRequest;
import com.example.backend.dto.StaffUpdateRequest;
import com.example.backend.dto.UserProfileResponse;
import com.example.backend.service.StaffManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/staff")
@RequiredArgsConstructor
public class StaffManageRestApi implements StaffManageController {
    private final StaffManageService staffManageService;

    @Override
    public ResponseEntity<List<UserProfileResponse>> getAllStaff() {
        return ResponseEntity.ok(staffManageService.getAllStaff());
    }

    @Override
    public ResponseEntity<ApiResponse> createStaff(@ModelAttribute StaffCreateRequest request) throws IOException {
        return ResponseEntity.ok(staffManageService.createStaff(request));
    }

    @Override
    public ResponseEntity<ApiResponse> updateStaff(@PathVariable String username, @ModelAttribute StaffUpdateRequest request) throws IOException {
        return ResponseEntity.ok(staffManageService.updateStaff(username, request));
    }

    @Override
    public ResponseEntity<ApiResponse> deleteStaff(@PathVariable String username) {
        return ResponseEntity.ok(staffManageService.deleteStaff(username));
    }
}
