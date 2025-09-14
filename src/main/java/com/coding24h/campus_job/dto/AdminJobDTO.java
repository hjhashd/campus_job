package com.coding24h.campus_job.dto;

import com.coding24h.campus_job.entity.Job;

import java.time.LocalDateTime;

public class AdminJobDTO {

        private Long jobId;
        private String title;
    private String jobType; // 改为字符串类型
    private String riskLevel; // 改为字符串类型
        private String salaryRange;
        private String companyName;
        private String companySize;
        private LocalDateTime createdAt;

    // 构造函数
    public AdminJobDTO() {}

    // 添加小写风险等级属性
    public String getRiskLevelLowerCase() {
        return riskLevel != null ? riskLevel.toLowerCase() : "";
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getSalaryRange() {
        return salaryRange;
    }

    public void setSalaryRange(String salaryRange) {
        this.salaryRange = salaryRange;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanySize() {
        return companySize;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}