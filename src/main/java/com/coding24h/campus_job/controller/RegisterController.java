package com.coding24h.campus_job.controller;

import com.coding24h.campus_job.dto.RegisterDTO;
import com.coding24h.campus_job.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody RegisterDTO dto) {
        Map<String, Object> response = new HashMap<>();

        // 1. 检查用户名是否已存在
        if (userService.findByUsername(dto.getUsername()) != null) {
            response.put("success", false);
            response.put("message", "用户名已存在");
            return ResponseEntity.badRequest().body(response);
        }

     /*   // 2. 检查邮箱是否已存在
        if (userService.findByEmail(dto.getEmail()) != null) {
            response.put("success", false);
            response.put("message", "邮箱已被注册");
            return ResponseEntity.badRequest().body(response);
        }*/

        try {
            // 3. 注册新用户
            userService.registerUser(dto);

            response.put("success", true);
            response.put("message", "注册成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "注册失败: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}