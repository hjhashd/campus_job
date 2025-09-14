package com.coding24h.campus_job.dto;


import com.coding24h.campus_job.entity.Job;

import java.util.List;

public class JobSearchDTO {
    private String keyword;
    private String salaryRange;
    private List<Job.JobType> jobTypes;
    private List<String> companySizes;
    private String creditScore;
    private int page = 1;
    private int pageSize = 10;
    private String sortBy = "newest"; // newest, salary, credit, rating

    // Getters and setters
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getSalaryRange() {
        return salaryRange;
    }

    public void setSalaryRange(String salaryRange) {
        this.salaryRange = salaryRange;
    }

    public List<Job.JobType> getJobTypes() {
        return jobTypes;
    }

    public void setJobTypes(List<Job.JobType> jobTypes) {
        this.jobTypes = jobTypes;
    }

    public List<String> getCompanySizes() {
        return companySizes;
    }

    public void setCompanySizes(List<String> companySizes) {
        this.companySizes = companySizes;
    }

    public String getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(String creditScore) {
        this.creditScore = creditScore;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}