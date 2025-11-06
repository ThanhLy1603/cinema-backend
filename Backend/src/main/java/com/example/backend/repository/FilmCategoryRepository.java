package com.example.backend.repository;

import com.example.backend.entity.FilmCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface FilmCategoryRepository extends JpaRepository<FilmCategory, UUID> {
    List<FilmCategory> findByFilmId(UUID filmId);
    @Modifying
    @Transactional
    @Query("DELETE FROM FilmCategory fc WHERE fc.film.id = :filmId")
    void deleteAllByFilmId(@Param("filmId") UUID filmId);
}
