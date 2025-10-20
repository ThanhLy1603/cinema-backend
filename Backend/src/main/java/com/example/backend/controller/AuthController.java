package com.example.backend.controller;

import com.example.backend.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthController {
    public ResponseEntity<Object> login(@RequestBody LoginRequest request);
    public ResponseEntity<ApiResponse> sendOtpRegister(@RequestBody OtpRequest request);
    public ResponseEntity<ApiResponse> verifyOtpRegister(@RequestBody VerifyOtpRequest request);
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest request);
}
