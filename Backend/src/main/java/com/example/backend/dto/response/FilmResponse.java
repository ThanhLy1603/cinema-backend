package com.example.backend.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record FilmResponse(
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
        String status
) {}