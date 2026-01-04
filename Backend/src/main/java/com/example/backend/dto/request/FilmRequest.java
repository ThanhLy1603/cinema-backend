package com.example.backend.dto.request;

import java.time.LocalDate;
import java.util.UUID;

public record FilmRequest(
        UUID id,
        String name,
        String country,
        String director,
        String actor,
        String description,
        Integer duration,
        String poster,
        String trailer,
        LocalDate releaseDate,
        String status,
        boolean isDeleted
) {// Phương thức copy record, thay đổi giá trị poster
    public FilmRequest withPoster(String newPoster) {
        return new FilmRequest(id, name, country, director, actor, description, duration,
                newPoster, trailer, releaseDate, status, isDeleted);
    }
    // Phương thức copy record, thay đổi giá trị trailer
    public FilmRequest withTrailer(String newTrailer) {
        return new FilmRequest(id, name, country, director, actor, description, duration,
                poster, newTrailer, releaseDate, status, isDeleted);
    }
}