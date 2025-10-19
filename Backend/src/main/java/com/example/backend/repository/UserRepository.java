package com.example.backend.repository;

import com.example.backend.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {
    Optional<Users> findByUsername(String name);
    Optional<Users> findByEmail(String email);
    Boolean existsByUsername(String username);
}
