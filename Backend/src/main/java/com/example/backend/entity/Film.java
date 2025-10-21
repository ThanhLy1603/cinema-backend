package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "films")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Film {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "UNIQUEIDENTIFIER")
    private UUID id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "country", length = 255)
    private String country;

    @Column(name = "director", columnDefinition = "NVARCHAR(MAX)")
    private String director;

    @Column(name = "description", columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "poster", length = 255)
    private String poster;

    @Column(name = "trailer", length = 255)
    private String trailer;

    @Column(name = "release_date")
    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "is_deleted",nullable = false)
    private boolean isDeleted;

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<FilmCategory> filmCategories;
}
