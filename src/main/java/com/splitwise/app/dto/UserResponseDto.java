package com.splitwise.app.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String profilePictureUrl;
    private boolean emailVerified;
    private String status;
    private LocalDateTime createdAt;
}