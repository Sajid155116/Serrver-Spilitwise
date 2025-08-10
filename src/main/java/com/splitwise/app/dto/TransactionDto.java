package com.splitwise.app.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public class TransactionDto {
    private String id;
    private String groupId;
    private String description;
    private BigDecimal amount;
    private String currency;
    private String paidById;
    private LocalDateTime date;
    private String category;
    private String notes;
    private Set<String> splitUserIds;

    // Default constructor
    public TransactionDto() {}

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPaidById() {
        return paidById;
    }

    public void setPaidById(String paidById) {
        this.paidById = paidById;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Set<String> getSplitUserIds() {
        return splitUserIds;
    }

    public void setSplitUserIds(Set<String> splitUserIds) {
        this.splitUserIds = splitUserIds;
    }
}