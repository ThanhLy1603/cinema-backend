package com.example.backend.repository;

import com.example.backend.entity.UserRole;
import com.example.backend.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
}
