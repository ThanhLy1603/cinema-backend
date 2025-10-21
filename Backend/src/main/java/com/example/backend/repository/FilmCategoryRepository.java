package com.example.backend.repository;

import com.example.backend.entity.FilmCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FilmCategoryRepository extends JpaRepository<FilmCategory, UUID> {
}
