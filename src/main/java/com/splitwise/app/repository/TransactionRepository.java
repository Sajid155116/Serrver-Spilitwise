package com.splitwise.app.repository;

import com.splitwise.app.entity.Group;
import com.splitwise.app.entity.Transaction;
import com.splitwise.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    @Query("SELECT t FROM Transaction t WHERE t.payer = :user")
    List<Transaction> findByPayer(@Param("user") User user);
    
    @Query("SELECT t FROM Transaction t WHERE t.group = :group")
    List<Transaction> findByGroup(@Param("group") Group group);
    
    @Query("SELECT t FROM Transaction t WHERE t.group = :group ORDER BY t.transactionDate DESC")
    List<Transaction> findByGroupOrderByTransactionDateDesc(@Param("group") Group group);
    
    @Query("SELECT t FROM Transaction t WHERE t.type = :type")
    List<Transaction> findByType(@Param("type") Transaction.TransactionType type);
    
    @Query("SELECT t FROM Transaction t WHERE t.status = :status")
    List<Transaction> findByStatus(@Param("status") Transaction.TransactionStatus status);
    
    @Query("SELECT t FROM Transaction t WHERE t.settled = :settled")
    List<Transaction> findBySettled(@Param("settled") boolean settled);
    
    @Query("SELECT t FROM Transaction t WHERE t.payer = :user AND t.group = :group")
    List<Transaction> findByPayerAndGroup(@Param("user") User user, @Param("group") Group group);
    
    @Query("SELECT t FROM Transaction t WHERE t.transactionDate BETWEEN :startDate AND :endDate")
    List<Transaction> findByTransactionDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.payer = :user AND t.group = :group")
    BigDecimal getTotalAmountByPayerAndGroup(@Param("user") User user, @Param("group") Group group);
    
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.group = :group")
    BigDecimal getTotalAmountByGroup(@Param("group") Group group);
    
    @Query("SELECT t FROM Transaction t JOIN t.splits ts WHERE ts.user = :user")
    List<Transaction> findTransactionsInvolvingUser(@Param("user") User user);
}