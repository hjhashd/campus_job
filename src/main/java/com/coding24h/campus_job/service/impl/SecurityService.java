package com.coding24h.campus_job.service.impl;


import com.coding24h.campus_job.entity.CustomUserDetails;
import com.coding24h.campus_job.entity.User;
import com.coding24h.campus_job.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class SecurityService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public SecurityService(UserService userService) {
        this.userService = userService;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        // 创建权限集合
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        // 添加ROLE_前缀
        String role = user.getRole();
        if (!role.startsWith("ROLE_")) {
            role = "ROLE_" + role;
        }
        authorities.add(new SimpleGrantedAuthority(role));

        return new CustomUserDetails(user);
    }
}