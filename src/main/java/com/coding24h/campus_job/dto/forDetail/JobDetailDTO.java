package com.coding24h.campus_job.dto.forDetail;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class JobDetailDTO {
    private Long jobId;
    private String title;
    private String description;
    private String jobType;
    private String riskLevel;
    private Boolean wageGuarantee;
    private String salaryRange;
    private String location;
    private String experienceRequirement;
    private String educationRequirement;
    private List<String> benefits;

    // 公司信息
    private Long companyUserId;
    private String companyName;
    private String financingStage;
    private String industry;
    private String headquarters;
    private String companySize;

    // 公司评分信息
    private Double companyRating;
    private Integer creditScore;
    private Integer reviewCount;

    // 相似职位
    private List<SimilarJobDTO> similarJobs;

    // 图表数据
    private Map<String, Long> salaryDistribution;        // 薪资分布数据
    private Map<Integer, Long> ratingDistribution;       // 评分分布数据
    private Map<String, BigDecimal> creditTrend;         // 信用趋势数据

    // 新增评价相关字段
    private Double avgRating;

    private Double jobRating;      // 该职位的平均评分
    private Integer jobReviewCount; // 该职位的评价数量


    public Double getJobRating() {
        return jobRating;
    }

    public void setJobRating(Double jobRating) {
        this.jobRating = jobRating;
    }

    public Integer getJobReviewCount() {
        return jobReviewCount;
    }

    public void setJobReviewCount(Integer jobReviewCount) {
        this.jobReviewCount = jobReviewCount;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }

    public Map<String, Long> getSalaryDistribution() {
        return salaryDistribution;
    }

    public void setSalaryDistribution(Map<String, Long> salaryDistribution) {
        this.salaryDistribution = salaryDistribution;
    }

    public Map<Integer, Long> getRatingDistribution() {
        return ratingDistribution;
    }

    public void setRatingDistribution(Map<Integer, Long> ratingDistribution) {
        this.ratingDistribution = ratingDistribution;
    }

    public Map<String, BigDecimal> getCreditTrend() {
        return creditTrend;
    }

    public void setCreditTrend(Map<String, BigDecimal> creditTrend) {
        this.creditTrend = creditTrend;
    }

    // 内部类 SimilarJobDTO
    public static class SimilarJobDTO {
        private Long jobId;
        private String title;
        private String salaryRange;
        private String companyName;

        // SimilarJobDTO 的 getter 和 setter 方法
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
    }

    // JobDetailDTO 的 getter 和 setter 方法
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

    public Boolean getWageGuarantee() {
        return wageGuarantee;
    }

    public void setWageGuarantee(Boolean wageGuarantee) {
        this.wageGuarantee = wageGuarantee;
    }

    public String getSalaryRange() {
        return salaryRange;
    }

    public void setSalaryRange(String salaryRange) {
        this.salaryRange = salaryRange;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public Long getCompanyUserId() {
        return companyUserId;
    }

    public void setCompanyUserId(Long companyUserId) {
        this.companyUserId = companyUserId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getCompanySize() {
        return companySize;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    public Double getCompanyRating() {
        return companyRating;
    }

    public void setCompanyRating(Double companyRating) {
        this.companyRating = companyRating;
    }

    public Integer getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public List<SimilarJobDTO> getSimilarJobs() {
        return similarJobs;
    }

    public void setSimilarJobs(List<SimilarJobDTO> similarJobs) {
        this.similarJobs = similarJobs;
    }
}