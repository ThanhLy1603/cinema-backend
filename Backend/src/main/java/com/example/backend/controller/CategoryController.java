package com.example.backend.controller;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.CategoryManageResponse;
import com.example.backend.entity.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface CategoryController {
    public ResponseEntity<List<CategoryManageResponse>> getAllActive();
    public  ResponseEntity<ApiResponse> delete (UUID id);
    public ResponseEntity<ApiResponse> create(@RequestBody Category category) ;

}
