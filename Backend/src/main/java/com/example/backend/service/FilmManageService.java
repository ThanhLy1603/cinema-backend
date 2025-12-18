package com.example.backend.service;

import com.example.backend.dto.request.FilmManageRequest;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.dto.response.CategoryManageResponse;
import com.example.backend.dto.response.FilmManageResponse;
import com.example.backend.entity.FilmCategory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.example.backend.entity.Category;
import com.example.backend.entity.Film;
import com.example.backend.repository.CategoryRepository;
import com.example.backend.repository.FilmCategoryRepository;
import com.example.backend.repository.FilmRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmManageService {
    private final FilmRepository filmRepository;
    private final CategoryRepository categoryRepository;
    private final FileStorageService fileStorageService;
    private final FilmCategoryRepository filmCategoryRepository;
    private final ObjectMapper objectMapper;

    // Cập nhật phim
    @Transactional
    public ApiResponse updateFilm(UUID id, FilmManageRequest request) throws IOException {
        Film existingFilm = filmRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Film not found with id " + id));

        System.out.println("existingFilm: " + existingFilm);
        System.out.println("filmRequest: " + request);
        System.out.println("categories id JSON: " + request.categoriesIdJSON());

        // Cập nhật thông tin từ request
        existingFilm.setName(request.name());
        existingFilm.setCountry(request.country());
        existingFilm.setDirector(request.director());
        existingFilm.setActor(request.actor());
        existingFilm.setDescription(request.description());
        existingFilm.setDuration(request.duration());
        existingFilm.setReleaseDate(request.releaseDate());
        existingFilm.setStatus(request.status());

        if (request.poster() != null && !request.poster().isEmpty()) {
            String poster = fileStorageService.saveFile(request.poster());
            existingFilm.setPoster(poster);
        }

        if (request.trailer() != null && !request.trailer().isEmpty()) {
            String trailer = fileStorageService.saveFile(request.trailer());
            existingFilm.setTrailer(trailer);
        }

        List<UUID> newCategoriesId = objectMapper.readValue(
                request.categoriesIdJSON(),
                new TypeReference<>(){}
        );

        filmCategoryRepository.deleteAllByFilmId(existingFilm.getId());

        for (UUID categoryId : newCategoriesId) {
            Category category =  categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new EntityNotFoundException("Category " + categoryId));

            FilmCategory filmCategory = new FilmCategory();
            filmCategory.setCategory(category);
            filmCategory.setFilm(existingFilm);
            filmCategoryRepository.save(filmCategory);
        }

        filmRepository.save(existingFilm);

        return new ApiResponse("sucess", "Sửa phim thành công");
    }

    @Transactional
    public ApiResponse createFilm(FilmManageRequest request) throws IOException {
        System.out.println("film request: " + request);
        System.out.println("categories JSON: " + request.categoriesIdJSON());
        Film film = new Film();
        film.setName(request.name());
        film.setCountry(request.country());
        film.setDirector(request.director());
        film.setActor(request.actor());
        film.setDescription(request.description());
        film.setDuration(request.duration());
        film.setReleaseDate(request.releaseDate());
        film.setStatus(request.status());
        film.setDeleted(false);

        if (request.poster() != null && !request.poster().isEmpty()) {
            String poster = fileStorageService.saveFile(request.poster());
            film.setPoster(poster);
        }

        if (request.trailer() != null && !request.trailer().isEmpty()) {
            String trailer = fileStorageService.saveFile(request.trailer());
            film.setTrailer(trailer);
        }

        filmRepository.save(film);

        List<UUID> categoriesId = objectMapper.readValue(
                request.categoriesIdJSON(),
                new TypeReference<>() {}
        );

        for (UUID categoryId : categoriesId) {
            Category category = categoryRepository.findById(categoryId).orElse(null);

            FilmCategory filmCategory = new FilmCategory();
            filmCategory.setCategory(category);
            filmCategory.setFilm(film);

            filmCategoryRepository.save(filmCategory);
        }

        System.out.println("categories ID: " + categoriesId);

        return new ApiResponse("success", "Thêm phim mới thành công");
    }

    @Transactional
    public List<FilmManageResponse> getAllFilms() {
        List<FilmManageResponse> films = filmRepository.findByIsDeletedFalse().stream()
                .map(this::toFilmManageResponse)
                .collect(Collectors.toList());

        return films;
    }

    @Transactional
    public List<CategoryManageResponse> getAllCategories() {
        List<CategoryManageResponse> categories = categoryRepository.findByIsDeletedIsFalse().stream()
                .map(this::toCategoryResponse)
                .collect(Collectors.toList());
        return categories;
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

    // Xóa mềm phim (chuyển isDeleted thành true)
    // Xóa mềm phim (chuyển isDeleted thành true)
    @Transactional
    public ApiResponse deleteFilm(UUID id) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Film not found with id " + id));

        film.setDeleted(true); // Xóa mềm
        filmRepository.save(film);

        return new ApiResponse("success", "Xoá phim thành công");
    }

    private CategoryManageResponse toCategoryResponse(Category category) {
        return new CategoryManageResponse(category.getId(), category.getName());
    }

    private FilmManageResponse toFilmManageResponse(Film film) {
        Set<CategoryManageResponse> categories = film.getCategories().stream()
                .map(category -> toCategoryResponse(category)).collect(Collectors.toSet());
        return new FilmManageResponse(
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
                categories
        );
    }
}
