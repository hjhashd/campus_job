package com.coding24h.campus_job.controller;

import com.coding24h.campus_job.dto.auth.AuthRequest;
import com.coding24h.campus_job.dto.auth.AuthStatusResponse;
import com.coding24h.campus_job.dto.auth.AuthSubmitResponse;
import com.coding24h.campus_job.entity.CustomUserDetails;
import com.coding24h.campus_job.service.CompanyAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Controller
public class CompanyController {

    @Autowired
    private CompanyAuthService authService;

    @GetMapping("/company/auth")
    public String authPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails) {
                CustomUserDetails userDetails = (CustomUserDetails) principal;
                Long userId = userDetails.getUserId();
                model.addAttribute("currentUserId", userId);

                AuthStatusResponse authStatus = authService.getAuthStatus(userId);
                String status = authStatus.getStatus();
                model.addAttribute("authStatus", status);

                if ("REJECTED".equals(status)) {
                    model.addAttribute("rejectReason", authStatus.getRejectReason());
                }


                return "nav/auth";
            }
        }
        return "redirect:/login"; // 如果用户未登录或认证失败，重定向到登录页面
    }

    @GetMapping("/path/to/post-job")
    public String index() {
        return "nav/publish_job";
    }

    @GetMapping("/company/center")
    public String goToCenter() {

        return "nav/company_center";
    }

}