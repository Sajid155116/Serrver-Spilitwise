package com.splitwise.app.repository;

import com.splitwise.app.entity.Group;
import com.splitwise.app.entity.Settlement;
import com.splitwise.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SettlementRepository extends JpaRepository<Settlement, Long> {
    
    @Query("SELECT s FROM Settlement s WHERE s.payer = :user")
    List<Settlement> findByPayer(@Param("user") User user);
    
    @Query("SELECT s FROM Settlement s WHERE s.payee = :user")
    List<Settlement> findByPayee(@Param("user") User user);
    
    @Query("SELECT s FROM Settlement s WHERE s.payer = :user OR s.payee = :user")
    List<Settlement> findByUser(@Param("user") User user);
    
    @Query("SELECT s FROM Settlement s WHERE s.group = :group")
    List<Settlement> findByGroup(@Param("group") Group group);
    
    @Query("SELECT s FROM Settlement s WHERE s.status = :status")
    List<Settlement> findByStatus(@Param("status") Settlement.SettlementStatus status);
    
    @Query("SELECT s FROM Settlement s WHERE s.type = :type")
    List<Settlement> findByType(@Param("type") Settlement.SettlementType type);
    
    @Query("SELECT s FROM Settlement s WHERE s.payer = :payer AND s.payee = :payee")
    List<Settlement> findByPayerAndPayee(@Param("payer") User payer, @Param("payee") User payee);
    
    @Query("SELECT s FROM Settlement s WHERE s.group = :group AND s.status = :status")
    List<Settlement> findByGroupAndStatus(@Param("group") Group group, @Param("status") Settlement.SettlementStatus status);
    
    @Query("SELECT SUM(s.amount) FROM Settlement s WHERE s.payer = :user AND s.status = 'COMPLETED'")
    BigDecimal getTotalSettledAmountByPayer(@Param("user") User user);
    
    @Query("SELECT SUM(s.amount) FROM Settlement s WHERE s.payee = :user AND s.status = 'COMPLETED'")
    BigDecimal getTotalReceivedAmountByPayee(@Param("user") User user);
    
    @Query("SELECT s FROM Settlement s WHERE (s.payer = :user OR s.payee = :user) AND s.group = :group")
    List<Settlement> findByUserAndGroup(@Param("user") User user, @Param("group") Group group);
}