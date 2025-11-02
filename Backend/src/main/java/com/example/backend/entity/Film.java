package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "films")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UNIQUEIDENTIFIER ")
    private UUID id;

    @Column(name = "name", nullable = false, length = 255, columnDefinition = "NVARCHAR(255)")
    private String name;

    @Column(name = "country", length = 255, columnDefinition = "NVARCHAR(100)")
    private String country;

    @Column(name = "director", columnDefinition = "NVARCHAR(255)")
    private String director;

    @Column(name = "actor", columnDefinition = "NVARCHAR(MAX)")
    private String actor;

    @Column(name = "description", columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "poster", length = 255)
    private String poster;

    @Column(name = "trailer", length = 255)
    private String trailer;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Builder.Default
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = true;

    @OneToMany(mappedBy = "film")
    private List<Schedule> schedules;

    @Builder.Default
    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<FilmCategory> filmCategories = new HashSet<>();

    public Set<Category> getCategories() {
        Set<Category> categories = new HashSet<>();

        for (FilmCategory filmCategory : filmCategories) {
            if (filmCategory.getCategory() != null && !filmCategory.getCategory().isDeleted()) {
                categories.add(filmCategory.getCategory());
            }
        }

        return categories;
    }
}
