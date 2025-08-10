package com.splitwise.app.service;

import com.splitwise.app.dto.GroupDto;
import com.splitwise.app.dto.GroupResponseDto;
import com.splitwise.app.dto.UserResponseDto;
import com.splitwise.app.entity.Group;
import com.splitwise.app.entity.User;
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
        User creator = userRepository.findByEmail(creatorEmail)
                .orElseThrow(() -> new UserNotFoundException("Creator not found"));

        Group group = new Group();
        group.setName(groupDto.getName());
        group.setDescription(groupDto.getDescription());
        group.setGroupImageUrl(groupDto.getGroupImageUrl());
        group.setType(Group.GroupType.valueOf(groupDto.getType().toUpperCase()));
        group.setCreatedBy(creator);
        group.setSimplifiedDebt(groupDto.isSimplifiedDebt());

        // Add members including creator
        Set<User> members = new HashSet<>();
        members.add(creator);

        if (groupDto.getMemberIds() != null && !groupDto.getMemberIds().isEmpty()) {
            for (Long memberId : groupDto.getMemberIds()) {
                User member = userRepository.findById(memberId)
                        .orElseThrow(() -> new UserNotFoundException("Member not found with id: " + memberId));
                members.add(member);
            }
        }

        group.setMembers(members);
        Group savedGroup = groupRepository.save(group);

        return convertToResponseDto(savedGroup);
    }

    public GroupResponseDto getGroupById(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + id));
        return convertToResponseDto(group);
    }

    public List<GroupResponseDto> getUserGroups(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        List<Group> groups = groupRepository.findByMembersContaining(user);
        return groups.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public GroupResponseDto updateGroup(Long id, GroupDto groupDto, String userEmail) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + id));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Check if user is creator or member
        if (!group.getCreatedBy().equals(user) && !group.getMembers().contains(user)) {
            throw new RuntimeException("User not authorized to update this group");
        }

        group.setName(groupDto.getName());
        group.setDescription(groupDto.getDescription());
        group.setGroupImageUrl(groupDto.getGroupImageUrl());
        group.setType(Group.GroupType.valueOf(groupDto.getType().toUpperCase()));
        group.setSimplifiedDebt(groupDto.isSimplifiedDebt());

        Group savedGroup = groupRepository.save(group);
        return convertToResponseDto(savedGroup);
    }

    public GroupResponseDto addMemberToGroup(Long groupId, Long memberId, String userEmail) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + groupId));

        User requestingUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        User newMember = userRepository.findById(memberId)
                .orElseThrow(() -> new UserNotFoundException("Member not found with id: " + memberId));

        // Check if requesting user is member of the group
        if (!group.getMembers().contains(requestingUser)) {
            throw new RuntimeException("User not authorized to add members to this group");
        }

        group.getMembers().add(newMember);
        Group savedGroup = groupRepository.save(group);

        return convertToResponseDto(savedGroup);
    }

    public GroupResponseDto removeMemberFromGroup(Long groupId, Long memberId, String userEmail) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + groupId));

        User requestingUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        User memberToRemove = userRepository.findById(memberId)
                .orElseThrow(() -> new UserNotFoundException("Member not found with id: " + memberId));

        // Check if requesting user is creator or the member being removed
        if (!group.getCreatedBy().equals(requestingUser) && !requestingUser.equals(memberToRemove)) {
            throw new RuntimeException("User not authorized to remove this member");
        }

        // Don't allow removing the creator
        if (group.getCreatedBy().equals(memberToRemove)) {
            throw new RuntimeException("Cannot remove group creator");
        }

        group.getMembers().remove(memberToRemove);
        Group savedGroup = groupRepository.save(group);

        return convertToResponseDto(savedGroup);
    }

    public void deleteGroup(Long id, String userEmail) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + id));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Only creator can delete the group
        if (!group.getCreatedBy().equals(user)) {
            throw new RuntimeException("Only group creator can delete the group");
        }

        group.setStatus(Group.GroupStatus.DELETED);
        groupRepository.save(group);
    }

    private GroupResponseDto convertToResponseDto(Group group) {
        GroupResponseDto dto = new GroupResponseDto();
        dto.setId(group.getId());
        dto.setName(group.getName());
        dto.setDescription(group.getDescription());
        dto.setGroupImageUrl(group.getGroupImageUrl());
        dto.setType(group.getType().toString());
        dto.setStatus(group.getStatus().toString());
        dto.setSimplifiedDebt(group.isSimplifiedDebt());
        dto.setCreatedAt(group.getCreatedAt());
        dto.setUpdatedAt(group.getUpdatedAt());

        // Convert creator to UserResponseDto
        UserResponseDto creatorDto = new UserResponseDto();
        creatorDto.setId(group.getCreatedBy().getId());
        creatorDto.setName(group.getCreatedBy().getName());
        creatorDto.setEmail(group.getCreatedBy().getEmail());
        dto.setCreatedBy(creatorDto);

        // Convert members to UserResponseDto list
        List<UserResponseDto> members = group.getMembers().stream()
                .map(member -> {
                    UserResponseDto memberDto = new UserResponseDto();
                    memberDto.setId(member.getId());
                    memberDto.setName(member.getName());
                    memberDto.setEmail(member.getEmail());
                    memberDto.setPhoneNumber(member.getPhoneNumber());
                    memberDto.setProfilePictureUrl(member.getProfilePictureUrl());
                    return memberDto;
                })
                .collect(Collectors.toList());
        dto.setMembers(members);

        dto.setTransactionCount(group.getTransactions().size());

        return dto;
    }
}