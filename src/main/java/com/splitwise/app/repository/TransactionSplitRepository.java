package com.splitwise.app.repository;

import com.splitwise.app.entity.TransactionSplit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionSplitRepository extends MongoRepository<TransactionSplit, String> {
    
    List<TransactionSplit> findByTransactionId(String transactionId);
    
    List<TransactionSplit> findByUserId(String userId);
    
    TransactionSplit findByTransactionIdAndUserId(String transactionId, String userId);
}