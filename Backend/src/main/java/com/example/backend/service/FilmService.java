package com.example.backend.service;

import com.example.backend.dto.response.ApiResponse;
import com.example.backend.dto.response.FilmResponse;
import com.example.backend.dto.response.CategoryManageResponse;
import com.example.backend.entity.Film;
import com.example.backend.repository.FilmRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmRepository filmRepository;
    private final FileStorageService fileStorageService;

    @Transactional
    public List<FilmResponse> getAllFilms() {
        List<FilmResponse> films = filmRepository.findByIsDeletedFalse().stream()
                .map(this::toFilmResponse)
                .collect(Collectors.toList());

        return films;
    }

    @Transactional
    public Object getFilmById(UUID id) {
        Film film = filmRepository.findFilmByIdAndIsDeletedFalse(id);

        return (film == null) ? new ApiResponse("error", "film not found") : toFilmResponse(film);
    }

    @Transactional
    public List<CategoryManageResponse> getCategoriesByFilmId(UUID id) {
        Film film = filmRepository.findFilmByIdAndIsDeletedFalse(id);

        if (film == null) {
            System.out.println(("Không tìm thấy phim với ID: " + id));
        }

        List<CategoryManageResponse> categories = film.getCategories().stream()
                .map(category -> new CategoryManageResponse(category.getId(), category.getName()))
                .collect(Collectors.toList());

        return categories;
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
                film.getStatus()
        );
    }
}
