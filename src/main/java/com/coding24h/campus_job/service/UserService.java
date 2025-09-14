package com.coding24h.campus_job.service;


import com.coding24h.campus_job.dto.LoginDTO;
import com.coding24h.campus_job.dto.RegisterDTO;
import com.coding24h.campus_job.entity.Page;
import com.coding24h.campus_job.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User findByUsername(String username);

    void registerUser(RegisterDTO dto);

    User getUserById(Long userId);

    void addUser(User user);
    void updateUser(User user);
    void deleteUser(Long userId);

    List<User> findUsers(int offset, int size, String identityType, String status, String search);

    // 统计用户数量
    int countUsers(String identityType, String status, String search);

    // 获取用户统计数据
    Map<String, Long> getUserStats();

    // 切换用户状态
    void toggleUserStatus(Long userId);

}