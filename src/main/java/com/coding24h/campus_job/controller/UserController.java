package com.coding24h.campus_job.controller;

import com.coding24h.campus_job.dto.PageResult;
import com.coding24h.campus_job.entity.User;
import com.coding24h.campus_job.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/users")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 用户管理主页面（自定义分页实现）
    @GetMapping("/list")
    public String userManagementPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String identityType,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String search,
            Model model) {

        int offset = (page - 1) * size;

        // 获取用户列表
        List<User> users = userService.findUsers(offset, size, identityType, status, search);

        // 获取用户总数
        int totalItems = userService.countUsers(identityType, status, search);

        // 计算总页数
        int totalPages = (int) Math.ceil((double) totalItems / size);

        // 获取用户统计信息
        Map<String, Long> stats = userService.getUserStats();

        // 添加模型属性
        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("stats", stats);

        // 保留筛选参数
        model.addAttribute("identityType", identityType);
        model.addAttribute("status", status);
        model.addAttribute("search", search);

        return "back/user_management";
    }


    @GetMapping("/{userId}")
    public String getUserDetail(
            @PathVariable Long userId,
            @RequestParam(required = false) String identityType,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String search,
            Model model) {


        try {
            User user = userService.getUserById(userId);
            model.addAttribute("user", user);

        } catch (Exception e) {
            model.addAttribute("error", "无法加载用户数据");
        }

        // 保留筛选参数
        model.addAttribute("identityType", identityType);
        model.addAttribute("status", status);
        model.addAttribute("search", search);

        return "back/user_detail";
    }


    // 更新用户信息
    @PostMapping("/{userId}/update")
    public String updateUser(@PathVariable Long userId,
                             @ModelAttribute User user,
                             @RequestParam(required = false) String identityType,
                             @RequestParam(required = false) String status,
                             @RequestParam(required = false) String search) {
        user.setUserId(userId);
        userService.updateUser(user);
        return buildRedirectUrl(identityType, status, search);
    }

    // 切换用户状态
    @PostMapping("/{userId}/toggle-status")
    public String toggleUserStatus(@PathVariable Long userId,
                                   @RequestParam(required = false) String identityType,
                                   @RequestParam(required = false) String status,
                                   @RequestParam(required = false) String search) {
        userService.toggleUserStatus(userId);
        return buildRedirectUrl(identityType, status, search);
    }

    // 删除用户
    @PostMapping("/{userId}/delete")
    public String deleteUser(@PathVariable Long userId,
                             @RequestParam(required = false) String identityType,
                             @RequestParam(required = false) String status,
                             @RequestParam(required = false) String search) {
        userService.deleteUser(userId);
        return buildRedirectUrl(identityType, status, search);
    }

    // 构建重定向URL（保留筛选参数）
    private String buildRedirectUrl(String identityType, String status, String search) {
        StringBuilder redirectUrl = new StringBuilder("redirect:/admin/users/list?");

        if (identityType != null && !identityType.isEmpty()) {
            redirectUrl.append("identityType=").append(identityType).append("&");
        }
        if (status != null && !status.isEmpty()) {
            redirectUrl.append("status=").append(status).append("&");
        }
        if (search != null && !search.isEmpty()) {
            redirectUrl.append("search=").append(search).append("&");
        }

        // 移除末尾多余的"&"
        if (redirectUrl.charAt(redirectUrl.length() - 1) == '&') {
            redirectUrl.deleteCharAt(redirectUrl.length() - 1);
        }

        return redirectUrl.toString();
    }
}