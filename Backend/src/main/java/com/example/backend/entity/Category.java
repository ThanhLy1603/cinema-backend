package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UNIQUEIDENTIFIER")
    private UUID id;

    @Column(name = "name", nullable = false, length = 255, columnDefinition = "NVARCHAR(255)")
    private String name;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;



    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<FilmCategory> filmCategory;


}
