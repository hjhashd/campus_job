package com.coding24h.campus_job.service.impl;

import com.coding24h.campus_job.dto.account.AccountUpdateDTO;
import com.coding24h.campus_job.dto.account.PasswordChangeDTO;
import com.coding24h.campus_job.entity.User;
import com.coding24h.campus_job.exception.InvalidPasswordException;
import com.coding24h.campus_job.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AccountService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountService(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Transactional
    public User updateUserProfile(String username, AccountUpdateDTO updateDTO) {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 更新基本信息
        user.setEmail(updateDTO.getEmail());
        user.setPhone(updateDTO.getPhone());
        user.setSchool(updateDTO.getSchool());
        user.setMajor(updateDTO.getMajor());
        user.setEducation(updateDTO.getEducation());
        user.setCompanyName(updateDTO.getCompanyName());
        user.setContactPerson(updateDTO.getContactPerson());
        user.setUpdatedAt(LocalDateTime.now());

        userMapper.update(user);
        return user;
    }

    @Transactional
    public void updateAvatar(String username, String avatarPath) {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        user.setAvatar(avatarPath);
        userMapper.update(user);
    }

    @Transactional
    public void changePassword(String username, PasswordChangeDTO passwordDTO) {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 验证当前密码
        if (!passwordEncoder.matches(passwordDTO.getCurrentPassword(), user.getPassword())) {
            throw new InvalidPasswordException("当前密码不正确");
        }

        // 验证新密码和确认密码是否一致
        if (!passwordDTO.getNewPassword().equals(passwordDTO.getConfirmPassword())) {
            throw new InvalidPasswordException("新密码和确认密码不一致");
        }

        // 更新密码
        String newPasswordEncoded = passwordEncoder.encode(passwordDTO.getNewPassword());
        user.setPassword(newPasswordEncoded);
        userMapper.update(user);
    }
}