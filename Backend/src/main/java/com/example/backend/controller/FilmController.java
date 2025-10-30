package com.example.backend.controller;

import com.example.backend.dto.FilmResponse;
import com.example.backend.dto.CategoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

public interface FilmController {
    public ResponseEntity<List<FilmResponse>> getAllFilms();
    public ResponseEntity<Object> getFilmById(@PathVariable UUID id);
    public ResponseEntity<List<CategoryResponse>> getCategoriesByFilmId(@PathVariable UUID id);
}
