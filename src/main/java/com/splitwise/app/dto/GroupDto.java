package com.splitwise.app.dto;

import java.util.Set;

public class GroupDto {
    private String id;
    private String name;
    private String description;
    private String createdBy;
    private Set<String> memberIds;
    private Set<String> adminIds;
    private String currency;

    // Default constructor
    public GroupDto() {}

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Set<String> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(Set<String> memberIds) {
        this.memberIds = memberIds;
    }

    public Set<String> getAdminIds() {
        return adminIds;
    }

    public void setAdminIds(Set<String> adminIds) {
        this.adminIds = adminIds;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}