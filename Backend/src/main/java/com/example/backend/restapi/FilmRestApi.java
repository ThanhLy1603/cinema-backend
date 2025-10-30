package com.example.backend.restapi;

import com.example.backend.controller.FilmController;
import com.example.backend.dto.FilmRequest;
import com.example.backend.dto.FilmResponse;
import com.example.backend.dto.CategoryResponse;
import com.example.backend.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // CREATE: POST /api/films
    @Override
    @PostMapping("")
    public ResponseEntity<FilmResponse> createFilm(@RequestBody FilmRequest filmRequest) {
        FilmResponse newFilm = filmService.createFilm(filmRequest);
        return ResponseEntity.status(201).body(newFilm);
    }

    // UPDATE: PUT /api/films/{id}
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateFilm(@PathVariable UUID id, @RequestBody FilmRequest filmRequest) {
        Object result = filmService.updateFilm(id, filmRequest);
        return ResponseEntity.ok(result);
    }

    // DELETE: DELETE /api/films/{id} (Xóa logic)
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFilm(@PathVariable UUID id) {
        Object result = filmService.deleteFilm(id);
        return ResponseEntity.ok(result);
    }
}
