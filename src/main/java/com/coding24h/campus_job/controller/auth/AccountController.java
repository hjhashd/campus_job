package com.coding24h.campus_job.controller.auth;

import com.coding24h.campus_job.dto.account.AccountUpdateDTO;
import com.coding24h.campus_job.dto.account.PasswordChangeDTO;
import com.coding24h.campus_job.entity.User;
import com.coding24h.campus_job.service.impl.AccountService;
import com.coding24h.campus_job.service.impl.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/account")
public class AccountController {

    private final AccountService accountService;
    private final FileStorageService fileStorageService;

    @Autowired
    public AccountController(AccountService accountService, FileStorageService fileStorageService) {
        this.accountService = accountService;
        this.fileStorageService = fileStorageService;
    }

    // 显示账号设置页面
    @GetMapping
    public String accountSettings(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = accountService.getUserByUsername(username);
        model.addAttribute("user", user);
        return "back/account";
    }

    // 更新用户基本信息
    @PostMapping("/update")
    public String updateProfile(@ModelAttribute("user") AccountUpdateDTO updateDTO,
                                RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            User updatedUser = accountService.updateUserProfile(username, updateDTO);
            redirectAttributes.addFlashAttribute("success", "个人信息更新成功！");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "更新失败: " + e.getMessage());
        }
        return "redirect:/admin/account";
    }

    // 更新用户头像
    @PostMapping("/avatar")
    public String updateAvatar(@RequestParam("avatarFile") MultipartFile avatarFile,
                               RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            String relativePath = fileStorageService.storeFile(avatarFile, "avatars");
            accountService.updateAvatar(username, relativePath);
            redirectAttributes.addFlashAttribute("success", "头像更新成功！");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "头像更新失败: " + e.getMessage());
        }
        return "redirect:/admin/account";
    }

    // 更改密码
    @PostMapping("/password")
    public String changePassword(@ModelAttribute PasswordChangeDTO passwordDTO,
                                 RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            accountService.changePassword(username, passwordDTO);
            redirectAttributes.addFlashAttribute("success", "密码更新成功！");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "密码更新失败: " + e.getMessage());
        }
        return "redirect:/admin/account";
    }
}