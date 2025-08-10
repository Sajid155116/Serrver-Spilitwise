package com.splitwise.app.repository;

import com.splitwise.app.entity.Group;
import com.splitwise.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    
    @Query("SELECT g FROM Group g WHERE g.createdBy = :user")
    List<Group> findByCreatedBy(@Param("user") User user);
    
    @Query("SELECT g FROM Group g JOIN g.members m WHERE m = :user")
    List<Group> findByMembersContaining(@Param("user") User user);
    
    @Query("SELECT g FROM Group g WHERE g.name LIKE %:name%")
    List<Group> findByNameContaining(@Param("name") String name);
    
    @Query("SELECT g FROM Group g WHERE g.type = :type")
    List<Group> findByType(@Param("type") Group.GroupType type);
    
    @Query("SELECT g FROM Group g WHERE g.status = :status")
    List<Group> findByStatus(@Param("status") Group.GroupStatus status);
    
    @Query("SELECT g FROM Group g JOIN g.members m WHERE m = :user AND g.status = :status")
    List<Group> findByMemberAndStatus(@Param("user") User user, @Param("status") Group.GroupStatus status);
    
    @Query("SELECT COUNT(g) FROM Group g JOIN g.members m WHERE m = :user")
    long countByMember(@Param("user") User user);
}