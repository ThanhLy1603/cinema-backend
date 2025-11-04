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
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return ResponseEntity.ok(filmManageService.getAllCategories());
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createFilm(@ModelAttribute FilmManageRequest request) throws IOException {
        return ResponseEntity.ok(filmManageService.createFilm(request));
    }

    @Override
    public ResponseEntity<FilmResponse> updateFilm(UUID id, FilmRequest filmRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteFilm(UUID id) {
        return null;
    }
}
