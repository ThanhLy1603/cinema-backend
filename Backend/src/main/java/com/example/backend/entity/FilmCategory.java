package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "film_categories", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"film_id", "category_id"}, name = "UQ_FilmCategory")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmCategory {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "UNIQUEIDENTIFIER")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", nullable = false, foreignKey = @ForeignKey(name = "FK_FC_Film"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Film film;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false, foreignKey = @ForeignKey(name = "FK_FC_Category"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Category category;
}
