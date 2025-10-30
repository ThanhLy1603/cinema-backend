package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.FilmRequest;
import com.example.backend.dto.FilmResponse;
import com.example.backend.dto.CategoryResponse;
import com.example.backend.entity.Film;
import com.example.backend.repository.FilmRepository;
import jakarta.persistence.EntityNotFoundException;
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
    private Film toFilmEntity(FilmRequest request) {
        // Nếu là tạo mới, ID sẽ là null hoặc không được dùng
        // Nếu là cập nhật, ID sẽ được sử dụng để tìm Entity hiện có
        return Film.builder()
                .id(request.id())
                .name(request.name())
                .country(request.country())
                .director(request.director())
                .actor(request.actor())
                .description(request.description())
                .duration(request.duration())
                .poster(request.poster())
                .trailer(request.trailer())
                .releaseDate(request.releaseDate())
                .status(request.status())
                .isDeleted(request.isDeleted())
                .build();
    }
    //--- CREATE (Thêm phim mới) ---
    @Transactional
    public FilmResponse createFilm(FilmRequest filmRequest) {
        Film newFilm = toFilmEntity(filmRequest);
        // Đảm bảo id là null khi tạo mới
        newFilm.setId(null);
        // Thiết lập các giá trị mặc định nếu cần
        newFilm.setDeleted(false);

        Film savedFilm = filmRepository.save(newFilm);
        return toFilmResponse(savedFilm);
    }

    // --- UPDATE (Chỉnh sửa phim) ---
    @Transactional
    public Object updateFilm(UUID id, FilmRequest filmRequest) {
        Film existingFilm = filmRepository.findFilmByIdAndIsDeletedFalse(id); // [cite: 570, 569]
        if (existingFilm == null) {
            return new ApiResponse("error", "Film not found or already deleted"); // [cite: 570]
        }

        // Cập nhật thông tin từ Request DTO vào Entity hiện có
        existingFilm.setName(filmRequest.name());
        existingFilm.setCountry(filmRequest.country());
        existingFilm.setDirector(filmRequest.director());
        existingFilm.setActor(filmRequest.actor());
        existingFilm.setDescription(filmRequest.description());
        existingFilm.setDuration(filmRequest.duration());
        existingFilm.setPoster(filmRequest.poster());
        existingFilm.setTrailer(filmRequest.trailer());
        existingFilm.setReleaseDate(filmRequest.releaseDate());
        existingFilm.setStatus(filmRequest.status());
        // Không cập nhật isDeleted ở đây, dùng deleteFilm() để xóa logic

        Film updatedFilm = filmRepository.save(existingFilm);
        return toFilmResponse(updatedFilm);
    }

    // --- DELETE (Xóa logic/Vô hiệu hóa phim) ---
    @Transactional
    public Object deleteFilm(UUID id) {
        Film existingFilm = filmRepository.findFilmByIdAndIsDeletedFalse(id); // [cite: 570, 569]
        if (existingFilm == null) {
            return new ApiResponse("error", "Film not found or already deleted"); // [cite: 570]
        }

        // Thực hiện xóa logic (chuyển isDeleted thành true)
        existingFilm.setDeleted(true);
        filmRepository.save(existingFilm);

        return new ApiResponse("success", "Film deleted successfully");
    }
}
