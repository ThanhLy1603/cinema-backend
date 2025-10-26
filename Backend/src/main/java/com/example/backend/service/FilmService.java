package com.example.backend.service;

import com.example.backend.dto.FilmResponse;
import com.example.backend.dto.CategoryResponse;
import com.example.backend.entity.Film;
import com.example.backend.repository.FilmRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmRepository filmRepository;

    @Transactional
    public ResponseEntity<List<FilmResponse>> getAllFilms() {
        List<FilmResponse> films = filmRepository.findAll().stream()
                .map(this::toFilmResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(films);
    }

    @Transactional
    public ResponseEntity<FilmResponse> getFilmById(UUID id) {
        Film film = filmRepository.findById(id).orElse(null);

        if (film == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(toFilmResponse(film));
    }

    @Transactional
    public ResponseEntity<List<CategoryResponse>> getCategoriesByFilmId(UUID id) {
        Film film = filmRepository.findById(id).orElse(null);

        if (film == null) {
            return ResponseEntity.notFound().build();
        }

        List<CategoryResponse> categories = film.getCategories().stream()
                .map(category -> new CategoryResponse(category.getId(), category.getName()))
                .collect(Collectors.toList());
        System.out.println("film: " + film);
        System.out.println("film categories: " + film.getCategories());
        System.out.println("film_categories: " + film.getFilmCategories());
        System.out.println("categories: " + categories);

        return ResponseEntity.ok(categories);
    }

    private FilmResponse toFilmResponse(Film film) {
        return new FilmResponse(
                film.getId(),
                film.getName(),
                film.getCountry(),
                film.getDirector(),
                film.getActor(),
                film.getDescription(),
                film.getDuration(),
                film.getPoster(),
                film.getTrailer(),
                film.getReleaseDate(),
                film.getStatus(),
                film.isDeleted()
        );
    }
}
