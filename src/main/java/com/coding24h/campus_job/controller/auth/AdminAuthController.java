package com.coding24h.campus_job.controller.auth;

import com.coding24h.campus_job.dto.PageResult;
import com.coding24h.campus_job.entity.AuthApplication;
import com.coding24h.campus_job.service.CompanyAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/auth")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminAuthController {

    private final CompanyAuthService authService;

    @Autowired
    public AdminAuthController(CompanyAuthService authService) {
        this.authService = authService;
    }

    // 认证管理主页面（自定义分页实现）
    @GetMapping("/list")
    public String authManagementPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String scoreRange,
            @RequestParam(required = false) String search,
            Model model) {

        int offset = (page - 1) * size;

        // 调用修改后的 service 方法
        List<AuthApplication> applications = authService.findApplications(
                offset,
                size,
                search,
                status,
                scoreRange
        );

        // 调用修改后的 count 方法
        int totalItems = authService.countApplications(search, status, scoreRange);

        int totalPages = (int) Math.ceil((double) totalItems / size);

        Map<String, Long> stats = authService.getAuthStats();

        model.addAttribute("pageData", applications);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("stats", stats);

        model.addAttribute("status", status);
        model.addAttribute("scoreRange", scoreRange);
        model.addAttribute("search", search);

        return "back/auth_management";
    }

    // 认证详情页面
    @GetMapping("/{authId}")
    public String getApplicationDetail(@PathVariable Long authId, Model model) {
        AuthApplication detail = authService.getAuthDetail(authId);
        model.addAttribute("auth", detail);
        return "back/auth_detail";
    }

    @PostMapping("/{authId}/approve")
    public String approveApplication(@PathVariable Long authId,
                                     @RequestParam(required = false) String status,
                                     @RequestParam(required = false) String scoreRange,
                                     @RequestParam(required = false) String search) {
        authService.approveAuth(authId);
        return buildRedirectUrl(status, scoreRange, search); // 返回带筛选参数的URL
    }

    @PostMapping("/{authId}/reject")
    public String rejectApplication(@PathVariable Long authId,
                                    @RequestParam String reason,
                                    @RequestParam(required = false) String status,
                                    @RequestParam(required = false) String scoreRange,
                                    @RequestParam(required = false) String search) {
        authService.rejectAuth(authId, reason);
        return buildRedirectUrl(status, scoreRange, search);
    }

    // 构建带筛选参数的重定向URL
    private String buildRedirectUrl(String status, String scoreRange, String search) {
        StringBuilder url = new StringBuilder("redirect:/admin/auth/list?");
        if (status != null) url.append("status=").append(status).append("&");
        if (scoreRange != null) url.append("scoreRange=").append(scoreRange).append("&");
        if (search != null) url.append("search=").append(search);
        return url.toString();
    }
}