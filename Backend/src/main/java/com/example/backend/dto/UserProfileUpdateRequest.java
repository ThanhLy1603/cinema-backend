package com.example.backend.dto;

public record UserProfileUpdateRequest(String lastName,
                                       String firstName,
                                       Boolean gender,
                                       String phone,
                                       String address,
                                       int day,
                                       int month,
                                       int year,
                                       String avatarUrl) {
}
