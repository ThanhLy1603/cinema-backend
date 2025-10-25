package com.example.backend.controller;

import com.example.backend.dto.UserProfileResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface UserProfileController {

    public ResponseEntity<Object> showProfile(@PathVariable String id);
}
