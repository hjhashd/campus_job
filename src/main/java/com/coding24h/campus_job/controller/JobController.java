package com.coding24h.campus_job.controller;

import com.coding24h.campus_job.dto.JobResultDTO;
import com.coding24h.campus_job.dto.JobSearchDTO;

import com.coding24h.campus_job.dto.PageResult;
import com.coding24h.campus_job.dto.forDetail.JobDetailDTO;
import com.coding24h.campus_job.entity.CustomUserDetails;
import com.coding24h.campus_job.entity.Job;
import com.coding24h.campus_job.entity.User;
import com.coding24h.campus_job.service.JobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }


    @GetMapping("/nav/search")
    public String searchPage() {
        // 不再加载数据，只返回空页面
        return "nav/search";
    }


    @GetMapping("/job/detail/{id}")
    public String getJobDetail(@PathVariable("id") Long id, Model model, Authentication authentication) {
        JobDetailDTO jobDetail = jobService.getJobDetailById(id);

        if (jobDetail == null) {
            return "error/404";
        }

        model.addAttribute("job", jobDetail);
        return "nav/detail";
    }

    @GetMapping("/chat")
    public String chatPage(
            @RequestParam Long jobId,
            @RequestParam Long companyUserId,
            Model model,
            Authentication authentication) {

        // 获取当前用户ID
        if (authentication != null && authentication.isAuthenticated()) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            model.addAttribute("currentUserId", userDetails.getUserId());
        }

        // 获取职位信息
        Job job = jobService.getJobById(jobId);
        if (job == null) {
            return "redirect:/error";
        }

        model.addAttribute("jobId", jobId);
        model.addAttribute("job", job);
        model.addAttribute("companyUserId", companyUserId);

        return "nav/chat";
    }

    private String generateRiskNotice(Integer creditScore) {
        if (creditScore == null) return "未认证";
        if (creditScore < 60) return "高风险";
        if (creditScore < 80) return "中风险";
        return "低风险";
    }

    @GetMapping("/job/search")
    public ResponseEntity<PageResult<JobResultDTO>> searchJobs(@ModelAttribute JobSearchDTO searchDTO) {
        PageResult<JobResultDTO> result = jobService.searchJobs(searchDTO);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/api/company/jobs")
    public ResponseEntity<?> publishJob(@RequestBody Job job) {
        try {
            // 从安全上下文中获取当前认证的用户
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("用户未登录");
            }

            // 获取当前用户（需要自定义UserDetails实现）
            CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();


            // 设置用户ID（从当前登录用户获取）
            job.setCompanyUserId(currentUser.getUserId());

            jobService.publishJob(job);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("发布职位失败: " + e.getMessage());
        }

    }

    // 添加企业职位管理页面
    @GetMapping("/company/jobs")
    public String jobManagementPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model,
            Authentication authentication) {

        // 获取当前企业用户ID
        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();
        Long companyUserId = currentUser.getUserId();

        // 获取职位列表
        PageResult<Job> pageResult = jobService.listJobsByCompany(companyUserId, page, size);

        // 添加数据到模型
        model.addAttribute("pageResult", pageResult);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalItems", pageResult.getTotalItems());
        model.addAttribute("totalPages", pageResult.getTotalPages());

        return "nav/company_center";
    }

    // 添加创建职位表单页面
    @GetMapping("/company/jobs/new")
    @PreAuthorize("hasRole('COMPANY')")
    public String newJobForm(Model model) {
        model.addAttribute("job", new Job());
        return "back/job_form";
    }

    // 添加编辑职位表单页面
    @GetMapping("/company/jobs/edit/{jobId}")
    @PreAuthorize("hasRole('COMPANY')")
    public String editJobForm(@PathVariable Long jobId, Model model, Authentication authentication) {
        // 获取当前企业用户ID
        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();
        Long companyUserId = currentUser.getUserId();

        // 获取职位
        Job job = jobService.getJobByIdAndCompany(jobId, companyUserId);
        model.addAttribute("job", job);
        return "back/job_form";
    }


}