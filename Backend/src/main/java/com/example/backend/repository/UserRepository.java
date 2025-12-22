//package com.example.backend.repository;
//
//import com.example.backend.entity.Users;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//import java.util.UUID;
//
//@Repository
//public interface UserRepository extends JpaRepository<Users, String> {
//    Optional<Users> findByUsername(String name);
//    Optional<Users> findByEmail(String email);
//    Boolean existsByUsername(String username);
//    Boolean existsByEmail(String email);
//}

package com.example.backend.repository;

import com.example.backend.entity.Users;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {

    // Nạp luôn userRoles và role khi truy vấn user theo username
    @EntityGraph(attributePaths = {"userRoles", "userRoles.role", "profile"})
    Optional<Users> findByUsername(String username);

    // Nạp luôn userRoles và role khi truy vấn user theo email
    @EntityGraph(attributePaths = {"userRoles", "userRoles.role", "profile"})
    Optional<Users> findByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query("SELECT u FROM Users u " +
            "JOIN u.userRoles ur " +
            "JOIN ur.role r " +
            "LEFT JOIN FETCH u.profile " +
            "WHERE r.name = 'STAFF' AND u.isDeleted = false")
    List<Users> findAllStaff();

    @EntityGraph(attributePaths = {"profile", "userRoles", "userRoles.role"})
    @Query("SELECT u FROM Users u " +
            "JOIN u.userRoles ur " +
            "JOIN ur.role r " +
            "WHERE u.username = :username AND r.name = 'STAFF' AND u.isDeleted = false")
    Optional<Users> findStaffByUsername(String username);
}
