package com.splitwise.app.repository;

import com.splitwise.app.entity.Transaction;
import com.splitwise.app.entity.TransactionSplit;
import com.splitwise.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransactionSplitRepository extends JpaRepository<TransactionSplit, Long> {
    
    @Query("SELECT ts FROM TransactionSplit ts WHERE ts.transaction = :transaction")
    List<TransactionSplit> findByTransaction(@Param("transaction") Transaction transaction);
    
    @Query("SELECT ts FROM TransactionSplit ts WHERE ts.user = :user")
    List<TransactionSplit> findByUser(@Param("user") User user);
    
    @Query("SELECT ts FROM TransactionSplit ts WHERE ts.transaction = :transaction AND ts.user = :user")
    TransactionSplit findByTransactionAndUser(@Param("transaction") Transaction transaction, @Param("user") User user);
    
    @Query("SELECT ts FROM TransactionSplit ts WHERE ts.settled = :settled")
    List<TransactionSplit> findBySettled(@Param("settled") boolean settled);
    
    @Query("SELECT ts FROM TransactionSplit ts WHERE ts.status = :status")
    List<TransactionSplit> findByStatus(@Param("status") TransactionSplit.SplitStatus status);
    
    @Query("SELECT ts FROM TransactionSplit ts WHERE ts.user = :user AND ts.settled = :settled")
    List<TransactionSplit> findByUserAndSettled(@Param("user") User user, @Param("settled") boolean settled);
    
    @Query("SELECT SUM(ts.amount) FROM TransactionSplit ts WHERE ts.user = :user AND ts.settled = false")
    BigDecimal getTotalUnsettledAmountByUser(@Param("user") User user);
    
    @Query("SELECT SUM(ts.amount) FROM TransactionSplit ts WHERE ts.transaction = :transaction")
    BigDecimal getTotalSplitAmountByTransaction(@Param("transaction") Transaction transaction);
    
    @Query("SELECT ts FROM TransactionSplit ts WHERE ts.user = :user AND ts.transaction.group.id = :groupId")
    List<TransactionSplit> findByUserAndGroupId(@Param("user") User user, @Param("groupId") Long groupId);
}