package com.splitwise.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDto {
    private String token;
    private String type = "Bearer";
    private UserResponseDto user;
    
    public AuthResponseDto(String token, UserResponseDto user) {
        this.token = token;
        this.user = user;
    }
}