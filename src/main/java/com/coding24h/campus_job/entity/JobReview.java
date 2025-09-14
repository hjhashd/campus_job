package com.coding24h.campus_job.entity;



import java.time.LocalDateTime;

public class JobReview {
    private Long reviewId;
    private Long jobId;
    private Long userId;
    private Integer rating;
    private Boolean wagePromptness;
    private Boolean envAuthenticity;
    private String reviewText;
    private Boolean verified;
    private LocalDateTime createdAt;

    private String username;

    // 添加职位标题字段
    private String jobTitle;
     private String      companyName;
     private  String location;
    private String salaryRange;


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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    // 添加 getter 和 setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Boolean getEnvAuthenticity() {
        return envAuthenticity;
    }

    public void setEnvAuthenticity(Boolean envAuthenticity) {
        this.envAuthenticity = envAuthenticity;
    }

    public Boolean getWagePromptness() {
        return wagePromptness;
    }

    public void setWagePromptness(Boolean wagePromptness) {
        this.wagePromptness = wagePromptness;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}