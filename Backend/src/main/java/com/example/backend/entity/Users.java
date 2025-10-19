package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users implements UserDetails {

    @Id
    @Column(length = 50, nullable = false)
    private String username;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 100, nullable = false, unique = true, columnDefinition = "NVARCHAR(100)")
    private String email;

    @Column(nullable = false)
    private Boolean enabled = true;

    // Quan hệ 1-1 với UserProfile
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserProfile profile;

    // Quan hệ 1-n với UserRole
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRole> userRoles = new HashSet<>();

    public Users(String username, String password, String email, Boolean enabled) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoles.stream()
                .map(userRole -> (GrantedAuthority) () -> "ROLE_" + userRole.getRole().getName())
                .collect(Collectors.toSet());
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return enabled; }
}
