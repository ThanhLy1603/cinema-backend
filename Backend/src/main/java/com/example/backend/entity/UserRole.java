package com.example.backend.entity;

import jakarta.persistence.*;

@Entity
@IdClass(UserRoleId.class)
@Table(name = "user_roles")
public class UserRole {

    @Id
    @Column(name = "username")
    private String username;

    @Id
    @Column(name = "role_id")
    private Integer roleId;

    // Liên kết đến User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    private Users user;

    // Liên kết đến Role
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    public UserRole() {}

    public UserRole(String username, Integer roleId) {
        this.username = username;
        this.roleId = roleId;
    }

    // Getters & Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Integer getRoleId() { return roleId; }
    public void setRoleId(Integer roleId) { this.roleId = roleId; }

    public Users getUser() { return user; }
    public void setUser(Users user) { this.user = user; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
