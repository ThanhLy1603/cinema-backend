package com.example.backend.dto;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public record FilmManageResponse(
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
        Set<CategoryManageResponse> categories
) {
}
