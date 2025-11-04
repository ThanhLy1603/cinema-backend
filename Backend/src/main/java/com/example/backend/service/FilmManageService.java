package com.example.backend.service;

import com.example.backend.dto.*;
import com.example.backend.entity.Category;
import com.example.backend.entity.Film;
import com.example.backend.entity.FilmCategory;
import com.example.backend.repository.CategoryRepository;
import com.example.backend.repository.FilmCategoryRepository;
import com.example.backend.repository.FilmRepository;
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

    @Transactional
    public ApiResponse createFilm(FilmManageRequest request) throws IOException {
        System.out.println("film request: " + request);
        Film film = new Film();
        film.setName(request.name());
        film.setCountry(request.country());
        film.setDirector(request.director());
        film.setActor(request.actor());
        film.setDescription(request.description());
        film.setDuration(request.duration());
        film.setReleaseDate(request.releaseDate());
        film.setStatus(request.status());

        // 2. Lưu poster/trailer nếu có
//        if (request.poster() != null && !request.poster().isEmpty()) {
//            String poster = fileStorageService.saveFile(request.poster());
//            film.setPoster(poster);
//        }
//        if (request.trailer() != null && !request.trailer().isEmpty()) {
//            String trailer = fileStorageService.saveFile(request.trailer());
//            film.setTrailer(trailer);
//        }

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
    public List<CategoryResponse> getAllCategories() {
        List<CategoryResponse> categories = categoryRepository.findByIsDeletedIsFalse().stream()
                .map(this::toCategoryResponse)
                .collect(Collectors.toList());
        return categories;
    }

    @Transactional
    public List<CategoryResponse> getCategoriesByFilmId(UUID id) {
        Film film = filmRepository.findFilmByIdAndIsDeletedFalse(id);

        if (film == null) {
            System.out.println(("Không tìm thấy phim với ID: " + id));
        }

        List<CategoryResponse> categories = film.getCategories().stream()
                .map(category -> new CategoryResponse(category.getId(), category.getName()))
                .collect(Collectors.toList());

        return categories;
    }



    // Cập nhật phim
    @Transactional
    public FilmManageResponse updateFilm(UUID id, FilmRequest filmRequest) {
        Film existingFilm = filmRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Film not found with id " + id));

        // Cập nhật thông tin từ request
        existingFilm.setName(filmRequest.name());
        existingFilm.setCountry(filmRequest.country());
        existingFilm.setDirector(filmRequest.director());
        existingFilm.setActor(filmRequest.actor());
        existingFilm.setDescription(filmRequest.description());
        existingFilm.setDuration(filmRequest.duration());
        existingFilm.setReleaseDate(filmRequest.releaseDate());
        existingFilm.setStatus(filmRequest.status());
        // Cập nhật poster và trailer
        existingFilm.setPoster(filmRequest.poster());
        existingFilm.setTrailer(filmRequest.trailer());

        return toFilmManageResponse(filmRepository.save(existingFilm));
    }

    // Xóa mềm phim (chuyển isDeleted thành true)
    // Xóa mềm phim (chuyển isDeleted thành true)
    @Transactional
    public void deleteFilm(UUID id) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Film not found with id " + id));

        film.setDeleted(true); // Xóa mềm
        filmRepository.save(film);
    }

    private CategoryResponse toCategoryResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }

    private FilmManageResponse toFilmManageResponse(Film film) {
        Set<CategoryResponse> categories = film.getCategories().stream()
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

    // Phương thức chuyển đổi FilmRequest sang Film Entity (cần thiết cho POST/PUT)
    private Film toFilmEntity(FilmRequest filmRequest) {
        return Film.builder()
                .id(filmRequest.id())
                .name(filmRequest.name())
                .country(filmRequest.country())
                .director(filmRequest.director())
                .actor(filmRequest.actor())
                .description(filmRequest.description())
                .duration(filmRequest.duration())
                .poster(filmRequest.poster())
                .trailer(filmRequest.trailer())
                .releaseDate(filmRequest.releaseDate())
                .status(filmRequest.status())
                .isDeleted(filmRequest.isDeleted())
                .build();
    }
}
