package com.example.backend.controller;

import com.example.backend.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface FilmManageController {
    // Thêm phương thức cho CRUD Admin:
    public ResponseEntity<List<FilmManageResponse>> getAllFilms();
    public ResponseEntity<List<CategoryResponse>> getAllCategories();
    public ResponseEntity<ApiResponse> createFilm(@RequestBody FilmManageRequest request) throws IOException;
    public ResponseEntity<FilmResponse> updateFilm(@PathVariable UUID id, @RequestBody FilmRequest filmRequest);
    public ResponseEntity<Void> deleteFilm(@PathVariable UUID id);
}
