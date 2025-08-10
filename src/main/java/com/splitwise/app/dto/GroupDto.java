package com.splitwise.app.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class GroupDto {
    
    @NotBlank(message = "Group name is required")
    @Size(min = 2, max = 100, message = "Group name must be between 2 and 100 characters")
    private String name;
    
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
    
    private String groupImageUrl;
    
    private String type; // GENERAL, TRIP, HOME, OFFICE, FRIENDS, COUPLE, OTHER
    
    private List<Long> memberIds;
    
    private boolean simplifiedDebt = true;
}