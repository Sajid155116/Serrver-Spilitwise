package com.splitwise.app.repository;

import com.splitwise.app.entity.Settlement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SettlementRepository extends MongoRepository<Settlement, String> {
    
    List<Settlement> findByGroupId(String groupId);
    
    List<Settlement> findByFromUserId(String fromUserId);
    
    List<Settlement> findByToUserId(String toUserId);
    
    List<Settlement> findByGroupIdAndFromUserId(String groupId, String fromUserId);
    
    List<Settlement> findByGroupIdAndToUserId(String groupId, String toUserId);
}