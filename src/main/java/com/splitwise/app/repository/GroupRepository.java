package com.splitwise.app.repository;

import com.splitwise.app.entity.Group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends MongoRepository<Group, String> {
    
    Optional<Group> findByName(String name);
    
    List<Group> findByMemberIdsContaining(String userId);
    
    List<Group> findByAdminIdsContaining(String userId);
    
    List<Group> findByCreatedBy(String createdBy);
}