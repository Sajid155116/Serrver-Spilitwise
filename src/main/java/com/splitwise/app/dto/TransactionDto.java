package com.splitwise.app.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TransactionDto {
    
    @NotBlank(message = "Description is required")
    @Size(min = 1, max = 255, message = "Description must be between 1 and 255 characters")
    private String description;
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;
    
    private String currency = "USD";
    
    private Long groupId;
    
    private String type; // EXPENSE, PAYMENT, SETTLEMENT
    
    private LocalDateTime transactionDate;
    
    @Size(max = 1000, message = "Notes cannot exceed 1000 characters")
    private String notes;
    
    private String receiptUrl;
    
    private String splitType = "EQUAL"; // EQUAL, EXACT, PERCENTAGE, SHARES
    
    private List<TransactionSplitDto> splits;
}