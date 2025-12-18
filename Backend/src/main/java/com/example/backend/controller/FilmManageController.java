package com.example.backend.controller;

import com.example.backend.dto.request.FilmManageRequest;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.dto.response.CategoryManageResponse;
import com.example.backend.dto.response.FilmManageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface FilmManageController {
    // Thêm phương thức cho CRUD Admin:
    public ResponseEntity<List<FilmManageResponse>> getAllFilms();
    public ResponseEntity<List<CategoryManageResponse>> getAllCategories();
    public ResponseEntity<ApiResponse> createFilm(@RequestBody FilmManageRequest request) throws IOException;
    public ResponseEntity<ApiResponse> updateFilm(@PathVariable UUID id, @ModelAttribute FilmManageRequest request) throws IOException;
    public ResponseEntity<ApiResponse> deleteFilm(@PathVariable UUID id);
}
