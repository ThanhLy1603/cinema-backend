package com.example.backend.restapi;

import com.example.backend.controller.FilmController;
import com.example.backend.dto.FilmResponse;
import com.example.backend.dto.CategoryResponse;
import com.example.backend.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/films")
@RequiredArgsConstructor
public class FilmRestApi implements FilmController {
    private final FilmService filmService;

    @Override
    @GetMapping("")
    public ResponseEntity<List<FilmResponse>> getAllFilms() {
        return ResponseEntity.ok(filmService.getAllFilms());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Object> getFilmById(@PathVariable UUID id) {
        return ResponseEntity.ok(filmService.getFilmById(id));
    }

    @Override
    @GetMapping("/{id}/categories")
    public ResponseEntity<List<CategoryResponse>> getCategoriesByFilmId(@PathVariable UUID id) {
        return  ResponseEntity.ok(filmService.getCategoriesByFilmId(id));
    }
}
