package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {

    @Id
    @Column(length = 50)
    private String username;

    @OneToOne
    @MapsId
    @JoinColumn(name = "username", referencedColumnName = "username")
    private Users user;

    @Column(name = "full_name", length = 100, columnDefinition = "NVARCHAR(100)")
    private String fullName;

    private Boolean gender;

    @Column(length = 20, columnDefinition = "NVARCHAR(20)")
    private String phone;

    @Column(length = 255, columnDefinition = "NVARCHAR(255)")
    private String address;

    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Column(name = "avatar_url", length = 255, columnDefinition = "NVARCHAR(255)")
    private String avatarUrl;

    public UserProfile(String username, String fullName, boolean gender, String number, String address, Date birthday, String avatarUrl) {
    }
}
