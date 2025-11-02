package com.example.backend.controller;

import com.example.backend.dto.CategoryResponse;
import com.example.backend.dto.FilmManageResponse;
import com.example.backend.dto.FilmRequest;
import com.example.backend.dto.FilmResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface FilmManageController {
    // Thêm phương thức cho CRUD Admin:
    public ResponseEntity<List<FilmManageResponse>> getAllFilms();
    public ResponseEntity<List<CategoryResponse>> getAllCategories();
    public ResponseEntity<FilmResponse> createFilm(@RequestBody FilmRequest filmRequest);
    public ResponseEntity<FilmResponse> updateFilm(@PathVariable UUID id, @RequestBody FilmRequest filmRequest);
    public ResponseEntity<Void> deleteFilm(@PathVariable UUID id);
}
