package com.coding24h.campus_job.service.impl;


import com.coding24h.campus_job.dto.LoginDTO;
import com.coding24h.campus_job.dto.RegisterDTO;
import com.coding24h.campus_job.entity.Page;
import com.coding24h.campus_job.entity.User;
import com.coding24h.campus_job.mapper.RoleMapper;
import com.coding24h.campus_job.mapper.UserMapper;
import com.coding24h.campus_job.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, RoleMapper roleMapper) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }


    // 添加用户注册方法
    @Transactional
    @Override
    public void registerUser(RegisterDTO dto) {
        // 1. 检查用户名唯一性
        if (userMapper.findByUsername(dto.getUsername()) != null) {
            throw new RuntimeException("用户名已存在");
        }

        // 2. 创建用户实体
        User user = new User();
        user.setIdentityType(dto.getIdentityType());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone()); // 通用电话
        user.setAvatar("default-avatar.png"); // 默认头像

        // 3. 根据身份设置特殊字段
        if ("STUDENT".equals(dto.getIdentityType())) {
            user.setSchool(dto.getSchool());
            user.setMajor(dto.getMajor());
            user.setEducation(dto.getEducation());
        } else if ("COMPANY".equals(dto.getIdentityType())) {
            user.setCompanyName(dto.getCompanyName());
            user.setCompanySize(dto.getCompanySize());
            // contactPerson没有在user表中 - 需要处理
            user.setContactPerson(dto.getContactPerson());
        }

        // 4. 插入用户
        userMapper.insertUser(user);

        // 5. 分配角色
        assignUserRole(user.getUserId(), dto.getIdentityType());
    }

    private void assignUserRole(Long userId, String identityType) {
        String roleName = identityType;
        Long roleId = roleMapper.findRoleIdByName(roleName);

        if (roleId == null) {
            throw new RuntimeException("角色不存在: " + roleName);
        }

        roleMapper.insertUserRole(userId, roleId);
    }


    @Transactional
    @Override
    public void addUser(User user) {
        user.setPassword(user.getPassword());
        user.setCreatedAt(LocalDateTime.now());
        userMapper.insert(user);
    }

    // 3. 更新用户（保留密码处理逻辑）
    @Transactional
    @Override
    public void updateUser(User user) {
        user.setUpdatedAt(LocalDateTime.now());

        // 如果密码字段不为空且已更改，则加密
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(user.getPassword());
        }

        userMapper.update(user);
    }

    @Override
    public List<User> findUsers(int offset, int size, String identityType, String status, String search) {
        return userMapper.findUsersWithPaging(identityType, status, search, offset, size);
    }

    @Override
    public int countUsers(String identityType, String status, String search) {
        return userMapper.countUsers(identityType, status, search);
    }

    @Override
    public Map<String, Long> getUserStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("total", userMapper.countAllUsers());
        stats.put("active", userMapper.countUsersByStatus("ACTIVE"));
        stats.put("inactive", userMapper.countUsersByStatus("INACTIVE"));
        stats.put("students", userMapper.countUsersByIdentityType("STUDENT"));
        stats.put("companies", userMapper.countUsersByIdentityType("COMPANY"));
        stats.put("admins", userMapper.countUsersByIdentityType("ADMIN"));
        return stats;
    }

    @Override
    @Transactional
    public void toggleUserStatus(Long userId) {
        User user = userMapper.getUserById(userId);
        if (user == null) {
            throw new EntityNotFoundException("用户不存在");
        }

        // 使用整数值比较
        Integer newStatus = (user.getStatus() == 1) ? 0 : 1;
        userMapper.updateStatus(userId, newStatus == 1 ? "ACTIVE" : "INACTIVE");
    }

    @Override
    public User getUserById(Long userId) {
        User user = userMapper.getUserById(userId);
        if (user == null) {

            throw new EntityNotFoundException("用户不存在: " + userId);
        }
        return user;
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        // 先删除关联的角色
        roleMapper.deleteUserRoles(userId);
        // 再删除用户
        userMapper.delete(userId);
    }
}