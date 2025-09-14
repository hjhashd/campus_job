package com.coding24h.campus_job.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Job {
    private Long jobId;
    private String title;
    private String description;

    // 使用枚举类型更安全
    public enum JobType { FULL_TIME, PART_TIME, INTERNSHIP, REMOTE }
    private JobType jobType;

    public enum RiskLevel { LOW, MEDIUM, HIGH }
    private RiskLevel riskLevel;

    private boolean wageGuarantee;
    private String salaryRange;
    private String location;
    private Long companyUserId;
    private LocalDateTime createdAt;

    // 新增字段
    private String experienceRequirement;
    private String educationRequirement;
    private List<String> benefits; // 福利列表

    // 公司信息（非数据库字段，用于前端展示）
    private String companyName;
    private String companySize;
    private String financingStage;
    private String industry;
    private String headquarters;
    private Integer creditScore;

    // 评分信息
    private Double averageRating;
    private Integer reviewCount;

    // 构造函数
    public Job() {}

    // Getter/Setter 方法

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(RiskLevel riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getSalaryRange() {
        return salaryRange;
    }

    public void setSalaryRange(String salaryRange) {
        this.salaryRange = salaryRange;
    }

    public boolean isWageGuarantee() {
        return wageGuarantee;
    }

    public void setWageGuarantee(boolean wageGuarantee) {
        this.wageGuarantee = wageGuarantee;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompanySize() {
        return companySize;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    public Long getCompanyUserId() {
        return companyUserId;
    }

    public void setCompanyUserId(Long companyUserId) {
        this.companyUserId = companyUserId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getFinancingStage() {
        return financingStage;
    }

    public void setFinancingStage(String financingStage) {
        this.financingStage = financingStage;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    public void setHeadquarters(String headquarters) {
        this.headquarters = headquarters;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public Integer getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }
    // 注意：这里需要为所有字段添加，包括新增字段

    // 示例：枚举类型的getter/setter
    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public void setJobType(String jobTypeStr) {
        this.jobType = JobType.valueOf(jobTypeStr);
    }


    public String getExperienceRequirement() {
        return experienceRequirement;
    }

    public void setExperienceRequirement(String experienceRequirement) {
        this.experienceRequirement = experienceRequirement;
    }

    public String getEducationRequirement() {
        return educationRequirement;
    }

    public void setEducationRequirement(String educationRequirement) {
        this.educationRequirement = educationRequirement;
    }

    public List<String> getBenefits() {
        return benefits;
    }

    public void setBenefits(List<String> benefits) {
        this.benefits = benefits;
    }

    // 公司信息访问器
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}