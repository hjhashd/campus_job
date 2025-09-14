package com.coding24h.campus_job.controller.auth;

import com.coding24h.campus_job.dto.AdminJobDTO;
import com.coding24h.campus_job.dto.PageResult;
import com.coding24h.campus_job.dto.forDetail.JobDetailDTO;
import com.coding24h.campus_job.entity.Job;
import com.coding24h.campus_job.service.JobService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/jobs")
@PreAuthorize("hasRole('ADMIN')")
public class AdminJobController {

    private final JobService jobService;

    public AdminJobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public String adminJobManagement(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String jobType,
            @RequestParam(required = false) String riskLevel,
            Model model) {

        // 准备筛选参数
        Map<String, String> filters = new HashMap<>();
        if (search != null && !search.isEmpty()) filters.put("search", search);
        if (jobType != null && !jobType.isEmpty()) filters.put("jobType", jobType);
        if (riskLevel != null && !riskLevel.isEmpty()) filters.put("riskLevel", riskLevel);

        // 获取职位数据和统计数据
        PageResult<AdminJobDTO> pageResult = jobService.getAdminJobs(page, size, filters);
        Map<String, Long> stats = jobService.getJobStats();

        // 添加到模型
        model.addAttribute("pageResult", pageResult);
        model.addAttribute("stats", stats);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalItems", pageResult.getTotalItems());
        model.addAttribute("totalPages", pageResult.getTotalPages());

        // 保留筛选参数
        model.addAttribute("search", search);
        model.addAttribute("jobType", jobType);
        model.addAttribute("riskLevel", riskLevel);

        return "back/job_management";
    }


    @DeleteMapping("/{jobId}")
    public String deleteJob(@PathVariable Long jobId) {
        jobService.deleteJobAdmin(jobId);
        return "redirect:/admin/jobs";
    }

    // 构建带筛选参数的重定向URL
    private String buildRedirectUrl(String search, String jobType, String riskLevel) {
        StringBuilder url = new StringBuilder("redirect:/admin/jobs?");
        if (search != null) url.append("search=").append(search).append("&");
        if (jobType != null) url.append("jobType=").append(jobType).append("&");
        if (riskLevel != null) url.append("riskLevel=").append(riskLevel);
        return url.toString();
    }


    @GetMapping("/edit/{jobId}")
    public String editJob(@PathVariable Long jobId, Model model) {
        JobDetailDTO job = jobService.getJobDetailById(jobId);
        model.addAttribute("job", job);
        return "back/job_edit";
    }

    @PostMapping("/update/{jobId}")
    public String updateJob(@PathVariable Long jobId,
                            @ModelAttribute Job job,
                            RedirectAttributes redirectAttributes) {
        jobService.updateJob(job);
        redirectAttributes.addFlashAttribute("message", "职位更新成功");
        return "redirect:/admin/jobs/detail/" + jobId;
    }

    @GetMapping("/detail/{jobId}")
    public String jobDetail(@PathVariable Long jobId, Model model) {
        JobDetailDTO job = jobService.getJobDetailById(jobId);
        model.addAttribute("job", job);
        return "back/job_detail";
    }

}