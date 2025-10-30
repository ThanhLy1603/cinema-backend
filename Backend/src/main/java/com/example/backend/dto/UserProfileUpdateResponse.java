package com.example.backend.dto;

public record UserProfileUpdateResponse(String fullName,
                                        Boolean gender,
                                        String phone,
                                        String address,
                                        int day,
                                        int month,
                                        int year,
                                        String avatarUrl) {
}
