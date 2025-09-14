package com.coding24h.campus_job.service.impl;

import com.coding24h.campus_job.entity.CustomUserDetails;
import com.coding24h.campus_job.entity.JobReview;
import com.coding24h.campus_job.mapper.JobReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobReviewService {

    private final JobReviewMapper jobReviewMapper;

    @Autowired
    public JobReviewService(JobReviewMapper jobReviewMapper) {
        this.jobReviewMapper = jobReviewMapper;
    }

    public List<JobReview> getReviewsByCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Long userId = ((CustomUserDetails) authentication.getPrincipal()).getUserId();
            return jobReviewMapper.findByUserId(userId);
        }
        return List.of();
    }
}