package com.example.backend.controller;

import com.example.backend.dto.request.*;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.dto.response.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthController {
    public ResponseEntity<Object> login(@RequestBody LoginRequest request);
    public ResponseEntity<ApiResponse> sendOtpRegister(@RequestBody OtpRequest request);
    public ResponseEntity<ApiResponse> verifyOtpRegister(@RequestBody VerifyOtpRequest request);
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest request);
    public ResponseEntity<ApiResponse> sendOtpForgot(@RequestBody OtpRequest request);
    public ResponseEntity<ApiResponse> verifyOtpForgot(@RequestBody VerifyOtpRequest request);
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody ResetPasswordRequest request);
    public ResponseEntity<ApiResponse> checkUsername(@RequestBody CheckUsernameRequest request);
    public ResponseEntity<ApiResponse> checkEmail(@RequestBody CheckEmailRequest request);
}
