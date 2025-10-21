package com.example.backend.restapi;

import com.example.backend.controller.AuthController;
import com.example.backend.dto.*;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthRestApi implements AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomerDetailsService customerDetailsService;
    private final AuthService authService;
    private final OtpRegisterService otpRegisterService;
    private final OtpForgotPassService otpForgotPassService;
    private final UserRepository userRepository;

    @Override
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
            return ResponseEntity.ok(new ApiResponse("error", "Invalid username or password."));
        }
    }

    @Override
    @PostMapping("/send-otp")
    public ResponseEntity<ApiResponse> sendOtpRegister(@RequestBody OtpRequest request) {
        otpRegisterService.sendOtp(request.email());
        return ResponseEntity.ok(new ApiResponse("success", "Đã gửi mã OTP tới email!"));
    }

    @Override
    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse> verifyOtpRegister(@RequestBody VerifyOtpRequest request) {
        boolean valid = otpRegisterService.verifyOtp(request.email(), request.otp());
        return valid
                ? ResponseEntity.ok(new ApiResponse("success", "OTP hợp lệ!"))
                : ResponseEntity.badRequest().body(new ApiResponse("error", "OTP sai hoặc hết hạn!"));
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/send-otp-forgot")
    public ResponseEntity<ApiResponse> sendOtpForgot(@RequestBody OtpRequest request) {
        otpForgotPassService.sendOtp(request.email());
        return ResponseEntity.ok(new ApiResponse("success", "Đã gửi mã OTP tới email!"));
    }

    @PostMapping("/verify-otp-forgot")
    public ResponseEntity<ApiResponse> verifyOtpForgot(@RequestBody VerifyOtpRequest request)  {
        boolean valid = otpForgotPassService.verifyOtp(request.email(), request.otp());
        return valid
                ? ResponseEntity.ok(new ApiResponse("success", "OTP hợp lệ!"))
                : ResponseEntity.badRequest().body(new ApiResponse("error", "OTP sai hoặc hết hạn!"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody ResetPasswordRequest request) {
        return ResponseEntity.ok(authService.resetPassword(request));
    }

    @PostMapping("/check-username")
    public ResponseEntity<ApiResponse> checkUsername(@RequestBody CheckUsernameRequest request) {
        boolean exists = userRepository.existsByUsername(request.username());
        if (exists) {
            return ResponseEntity.ok(new ApiResponse("error", "Tên đăng nhập đã tồn tại"));
        }
        return ResponseEntity.ok(new ApiResponse("success", "Tên đăng nhập hợp lệ"));
    }

    @PostMapping("/check-email")
    public ResponseEntity<ApiResponse> checkEmail(@RequestBody CheckEmailRequest request) {
        boolean exists = userRepository.existsByEmail(request.email());

        System.out.println("email: " + userRepository.findByEmail(request.email()));
        if (exists) {
            return ResponseEntity.ok(new ApiResponse("error", "Email đã được sử dụng"));
        }
        return ResponseEntity.ok(new ApiResponse("success", "Email hợp lệ"));
    }
}
