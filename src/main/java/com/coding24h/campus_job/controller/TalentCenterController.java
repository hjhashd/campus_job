package com.coding24h.campus_job.controller;

import com.coding24h.campus_job.dto.PageResult;
import com.coding24h.campus_job.entity.User;
import com.coding24h.campus_job.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/company/talent")
public class TalentCenterController {

    private final UserService userService;

    @Autowired
    public TalentCenterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/center")
    public String talentCenter(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String education,
            @RequestParam(required = false) String school,
            @RequestParam(required = false) String major,
            @RequestParam(required = false) String search,
            Model model) {

        int offset = (page - 1) * size;
        String identityType = "STUDENT"; // 只查询学生用户

        // 获取学生列表
        List<User> talents = userService.findUsers(offset, size, identityType, "ACTIVE", search);

        // 获取学生总数
        int totalItems = userService.countUsers(identityType, "ACTIVE", search);

        // 计算总页数
        int totalPages = (int) Math.ceil((double) totalItems / size);

        // 创建分页结果对象
        PageResult<User> pageResult = new PageResult<>(
                totalItems,
                totalPages,
                page,
                size,
                talents
        );

        // 添加模型属性
        model.addAttribute("pageResult", pageResult);

        // 保留筛选参数
        model.addAttribute("education", education);
        model.addAttribute("school", school);
        model.addAttribute("major", major);
        model.addAttribute("search", search);

        return "job/talent_center";
    }

    @GetMapping("/resume/{userId}")
    public String viewResume(@PathVariable Long userId, Model model) {
        User student = userService.getUserById(userId);
        model.addAttribute("student", student);
        return "job/resume";
    }
}