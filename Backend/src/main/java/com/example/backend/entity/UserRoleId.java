package com.example.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserRoleId implements Serializable {

    @Column(name = "username", length = 50)
    private String username;

    @Column(name = "role_id")
    private Integer roleId;
}
