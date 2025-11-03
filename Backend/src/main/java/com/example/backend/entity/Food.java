package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "foods")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Food {
    @Id
    @Column(columnDefinition = "UNIQUEIDENTIFIER")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", length = 100, nullable = false, columnDefinition = "NVARCHAR(100)")
    private String name;

    @Column(name = "description", columnDefinition = "NVARCHAR(MAX)", nullable = false)
    private String description;

    @Column(name = "poster", columnDefinition = "NVARCHAR(255)", length = 255)
    private String poster;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;
}
