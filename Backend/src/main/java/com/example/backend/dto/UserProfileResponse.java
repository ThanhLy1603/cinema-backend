package com.example.backend.dto;

import com.example.backend.entity.UserProfile;

public record UserProfileResponse (String username,
                                   String fullName,
                                   Boolean gender,
                                   String phone,
                                   String address,
                                   String birthday,
                                   String avatarUrl,
                                   String email){
}
