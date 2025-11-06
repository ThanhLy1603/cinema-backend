package com.example.backend.restapi;

import com.example.backend.controller.FilmManageController;
import com.example.backend.dto.*;
import com.example.backend.service.FilmManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/films")
@RequiredArgsConstructor
public class FilmManageRestApi implements FilmManageController {
    private final FilmManageService filmManageService;

    @Override
    @GetMapping("")
    public ResponseEntity<List<FilmManageResponse>> getAllFilms() {
        return ResponseEntity.ok(filmManageService.getAllFilms());
    }

    @Override
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryManageResponse>> getAllCategories() {
        return ResponseEntity.ok(filmManageService.getAllCategories());
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createFilm(@ModelAttribute FilmManageRequest request) throws IOException {
        return ResponseEntity.ok(filmManageService.createFilm(request));
    }

    @Override
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateFilm(@PathVariable UUID id, @ModelAttribute FilmManageRequest request) throws IOException {
        return ResponseEntity.ok(filmManageService.updateFilm(id, request));
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteFilm(@PathVariable UUID id) {
        return  ResponseEntity.ok(filmManageService.deleteFilm(id));
    }
}
