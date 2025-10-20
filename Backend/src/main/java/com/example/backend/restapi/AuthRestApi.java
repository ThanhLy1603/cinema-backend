package com.example.backend.restapi;

import com.example.backend.dto.*;
import com.example.backend.entity.Users;
import com.example.backend.service.AuthService;
import com.example.backend.service.CustomerDetailsService;
import com.example.backend.service.JwtService;
import com.example.backend.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthRestApi {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomerDetailsService customerDetailsService;
    private final AuthService authService;
    private final OtpService otpService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest request) {
        try {
            var authToken = new UsernamePasswordAuthenticationToken(
                    request.username(),
                    request.password()
            );

            authenticationManager.authenticate(authToken);

            UserDetails userDetails = customerDetailsService.loadUserByUsername(request.username());
            System.out.println("User details: " + userDetails.getUsername() + ", " + userDetails.getPassword() + ", " + userDetails.getAuthorities());
            String token = jwtService.generateToken(userDetails);

            return ResponseEntity.ok(new LoginResponse(token));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of("error", "Invalid username and password"));
        }
    }
    @PostMapping("/send-otp")
    public ResponseEntity<RegisterResponse> sendOtp(@RequestBody OtpRequest request) {
        otpService.sendOtp(request.email());
        return ResponseEntity.ok(new RegisterResponse("success", "Đã gửi mã OTP tới email!"));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<RegisterResponse> verifyOtp(@RequestBody VerifyOtpRequest request) {
        boolean valid = otpService.verifyOtp(request.email(), request.otp());
        return valid
                ? ResponseEntity.ok(new RegisterResponse("success", "OTP hợp lệ!"))
                : ResponseEntity.badRequest().body(new RegisterResponse("error", "OTP sai hoặc hết hạn!"));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
}
