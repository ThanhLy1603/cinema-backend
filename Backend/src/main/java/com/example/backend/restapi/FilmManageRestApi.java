package com.example.backend.restapi;

import com.example.backend.controller.FilmManageController;
import com.example.backend.dto.CategoryResponse;
import com.example.backend.dto.FilmManageResponse;
import com.example.backend.dto.FilmRequest;
import com.example.backend.dto.FilmResponse;
import com.example.backend.service.FilmManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<FilmResponse> createFilm(FilmRequest filmRequest) {
        return null;
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
