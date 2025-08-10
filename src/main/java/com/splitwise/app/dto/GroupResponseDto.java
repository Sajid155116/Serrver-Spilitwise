package com.splitwise.app.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class GroupResponseDto {
    private Long id;
    private String name;
    private String description;
    private String groupImageUrl;
    private String type;
    private String status;
    private boolean simplifiedDebt;
    private UserResponseDto createdBy;
    private List<UserResponseDto> members;
    private int transactionCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}