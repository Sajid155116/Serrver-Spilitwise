package com.splitwise.app.service;

import com.splitwise.app.dto.GroupDto;
import com.splitwise.app.dto.GroupResponseDto;
import com.splitwise.app.entity.Group;
import com.splitwise.app.exception.UserNotFoundException;
import com.splitwise.app.repository.GroupRepository;
import com.splitwise.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public GroupResponseDto createGroup(GroupDto groupDto, String creatorEmail) {
        // Verify creator exists
        if (!userRepository.existsByEmail(creatorEmail)) {
            throw new UserNotFoundException("Creator not found");
        }

        Group group = new Group();
        group.setName(groupDto.getName());
        group.setDescription(groupDto.getDescription());
        group.setCreatedBy(creatorEmail);
        group.setCurrency(groupDto.getCurrency() != null ? groupDto.getCurrency() : "USD");

        // Add members including creator
        Set<String> members = new HashSet<>();
        members.add(creatorEmail);

        if (groupDto.getMemberIds() != null && !groupDto.getMemberIds().isEmpty()) {
            members.addAll(groupDto.getMemberIds());
        }

        group.setMemberIds(members);
        
        // Set creator as admin
        Set<String> admins = new HashSet<>();
        admins.add(creatorEmail);
        group.setAdminIds(admins);

        Group savedGroup = groupRepository.save(group);
        return convertToResponseDto(savedGroup);
    }

    public GroupResponseDto getGroupById(String id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + id));
        return convertToResponseDto(group);
    }

    public List<GroupResponseDto> getUserGroups(String email) {
        List<Group> groups = groupRepository.findByMemberIdsContaining(email);
        return groups.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public GroupResponseDto updateGroup(String id, GroupDto groupDto, String userEmail) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + id));

        // Check if user is creator or admin
        if (!group.getCreatedBy().equals(userEmail) && !group.getAdminIds().contains(userEmail)) {
            throw new RuntimeException("User not authorized to update this group");
        }

        group.setName(groupDto.getName());
        group.setDescription(groupDto.getDescription());
        group.setCurrency(groupDto.getCurrency());

        Group savedGroup = groupRepository.save(group);
        return convertToResponseDto(savedGroup);
    }

    public GroupResponseDto addMemberToGroup(String groupId, String memberEmail, String userEmail) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + groupId));

        // Check if user is creator or admin
        if (!group.getCreatedBy().equals(userEmail) && !group.getAdminIds().contains(userEmail)) {
            throw new RuntimeException("User not authorized to modify this group");
        }

        // Verify member exists
        if (!userRepository.existsByEmail(memberEmail)) {
            throw new UserNotFoundException("Member not found");
        }

        Set<String> members = new HashSet<>(group.getMemberIds());
        members.add(memberEmail);
        group.setMemberIds(members);

        Group savedGroup = groupRepository.save(group);
        return convertToResponseDto(savedGroup);
    }

    public GroupResponseDto removeMemberFromGroup(String groupId, String memberEmail, String userEmail) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + groupId));

        // Check if user is creator or admin
        if (!group.getCreatedBy().equals(userEmail) && !group.getAdminIds().contains(userEmail)) {
            throw new RuntimeException("User not authorized to modify this group");
        }

        // Cannot remove creator
        if (group.getCreatedBy().equals(memberEmail)) {
            throw new RuntimeException("Cannot remove group creator");
        }

        Set<String> members = new HashSet<>(group.getMemberIds());
        members.remove(memberEmail);
        group.setMemberIds(members);

        // Remove from admins if they were an admin
        Set<String> admins = new HashSet<>(group.getAdminIds());
        admins.remove(memberEmail);
        group.setAdminIds(admins);

        Group savedGroup = groupRepository.save(group);
        return convertToResponseDto(savedGroup);
    }

    public void deleteGroup(String id, String userEmail) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + id));

        // Only creator can delete group
        if (!group.getCreatedBy().equals(userEmail)) {
            throw new RuntimeException("Only group creator can delete the group");
        }

        groupRepository.deleteById(id);
    }

    private GroupResponseDto convertToResponseDto(Group group) {
        GroupResponseDto dto = new GroupResponseDto();
        dto.setId(group.getId());
        dto.setName(group.getName());
        dto.setDescription(group.getDescription());
        dto.setCreatedBy(group.getCreatedBy());
        dto.setMemberIds(group.getMemberIds());
        dto.setAdminIds(group.getAdminIds());
        dto.setCurrency(group.getCurrency());
        dto.setCreatedAt(group.getCreatedAt());
        dto.setUpdatedAt(group.getUpdatedAt());
        return dto;
    }
}