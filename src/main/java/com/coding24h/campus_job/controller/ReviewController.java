package com.coding24h.campus_job.controller;

import com.coding24h.campus_job.dto.PageResult;
import com.coding24h.campus_job.dto.forDetail.JobDetailDTO;
import com.coding24h.campus_job.entity.Job;
import com.coding24h.campus_job.entity.JobReview;
import com.coding24h.campus_job.entity.Page;
import com.coding24h.campus_job.service.JobService;
import com.coding24h.campus_job.service.impl.JobReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final JobReviewService jobReviewService;
    private final JobService jobService;

    @Autowired
    public ReviewController(JobReviewService jobReviewService, JobService jobService) {
        this.jobReviewService = jobReviewService;
        this.jobService = jobService;
    }

    @GetMapping
    public String getAllReviews(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        // 获取分页评价数据
        PageResult<JobReview> pageResult =  jobService.getAllReviewsWithJobInfo(page, size);

        // 添加到模型
        model.addAttribute("reviews", pageResult.getItems());
        model.addAttribute("currentPage", pageResult.getCurrentPage());
        model.addAttribute("totalPages", pageResult.getTotalPages());
        model.addAttribute("totalReviews", pageResult.getTotalItems());

        return "job/review"; // 模板名称
    }

}