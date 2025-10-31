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
    @Transactional
    public FilmResponse createFilm(FilmRequest filmRequest) {
        Film film = toFilmEntity(filmRequest);
        film.setId(UUID.randomUUID()); // Đảm bảo ID mới cho phim
        film.setDeleted(false); // Đảm bảo không bị xóa mặc định
        return toFilmResponse(filmRepository.save(film));
    }

    // Cập nhật phim
    @Transactional
    public FilmResponse updateFilm(UUID id, FilmRequest filmRequest) {
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

        return toFilmResponse(filmRepository.save(existingFilm));
    }

    // Xóa mềm phim (chuyển isDeleted thành true)
    @Transactional
    public void deleteFilm(UUID id) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Film not found with id " + id));

        film.setDeleted(true); // Xóa mềm
        filmRepository.save(film);
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
