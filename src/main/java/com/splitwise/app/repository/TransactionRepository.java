package com.splitwise.app.repository;

import com.splitwise.app.entity.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {
    
    List<Transaction> findByGroupId(String groupId);
    
    List<Transaction> findByPaidById(String paidById);
    
    List<Transaction> findByGroupIdAndPaidById(String groupId, String paidById);
    
    List<Transaction> findByGroupIdOrderByDateDesc(String groupId);
}