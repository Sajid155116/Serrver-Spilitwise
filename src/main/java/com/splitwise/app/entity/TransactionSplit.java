package com.splitwise.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_splits")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionSplit {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.00", message = "Amount cannot be negative")
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;
    
    @Column(name = "share_percentage", precision = 5, scale = 2)
    private BigDecimal sharePercentage;
    
    @Column(name = "share_count")
    private Integer shareCount;
    
    @Column(name = "is_settled")
    private boolean settled = false;
    
    @Column(name = "settled_at")
    private LocalDateTime settledAt;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SplitStatus status = SplitStatus.PENDING;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum SplitStatus {
        PENDING, ACCEPTED, REJECTED, SETTLED
    }
}