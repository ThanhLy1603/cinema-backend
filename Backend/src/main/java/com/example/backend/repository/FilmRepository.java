package com.example.backend.repository;

import com.example.backend.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FilmRepository extends JpaRepository<Film, UUID> {
    public List<Film> findByIsDeletedFalse();
    public Film findFilmByIdAndIsDeletedFalse(UUID id);
    List<Film> findByIsDeletedFalseOrderByNameAsc();

}

