package com.example.backend.dto.request;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.UUID;

public record FilmManageRequest(
        UUID id,
        String name,
        String country,
        String director,
        String actor,
        String description,
        Integer duration,
        MultipartFile poster,
        MultipartFile trailer,
        LocalDate releaseDate,
        String status,
        String categoriesIdJSON
) {
}
