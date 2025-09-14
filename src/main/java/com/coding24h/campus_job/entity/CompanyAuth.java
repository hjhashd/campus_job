package com.coding24h.campus_job.entity;

import java.time.LocalDateTime;

public class CompanyAuth {
    private Long authId;
    private Long userId;
    private String companySize;
    private String creditCode;
    private String companyDescription;
    private String businessLicense;
    private String taxCertificate;
    private String status = "PENDING";
    private Integer creditScore = 80;
    private LocalDateTime lastAuditTime;
    private Long auditorId;
    private String rejectReason;
    private Integer legalCaseCount = 0;
    private Integer wageDelayCount = 0;
    private String industry;

    // No-args constructor
    public CompanyAuth() {
    }

    // All-args constructor
    public CompanyAuth(Long authId, Long userId, String companySize, String creditCode, String companyDescription, String businessLicense, String taxCertificate, String status, Integer creditScore, LocalDateTime lastAuditTime, Long auditorId, String rejectReason, Integer legalCaseCount, Integer wageDelayCount, String industry) {
        this.authId = authId;
        this.userId = userId;
        this.companySize = companySize;
        this.creditCode = creditCode;
        this.companyDescription = companyDescription;
        this.businessLicense = businessLicense;
        this.taxCertificate = taxCertificate;
        this.status = status;
        this.creditScore = creditScore;
        this.lastAuditTime = lastAuditTime;
        this.auditorId = auditorId;
        this.rejectReason = rejectReason;
        this.legalCaseCount = legalCaseCount;
        this.wageDelayCount = wageDelayCount;
        this.industry = industry;
    }

    // Getters and Setters

    public Long getAuthId() {
        return authId;
    }

    public void setAuthId(Long authId) {
        this.authId = authId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCompanySize() {
        return companySize;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getTaxCertificate() {
        return taxCertificate;
    }

    public void setTaxCertificate(String taxCertificate) {
        this.taxCertificate = taxCertificate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }

    public LocalDateTime getLastAuditTime() {
        return lastAuditTime;
    }

    public void setLastAuditTime(LocalDateTime lastAuditTime) {
        this.lastAuditTime = lastAuditTime;
    }

    public Long getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Long auditorId) {
        this.auditorId = auditorId;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public Integer getLegalCaseCount() {
        return legalCaseCount;
    }

    public void setLegalCaseCount(Integer legalCaseCount) {
        this.legalCaseCount = legalCaseCount;
    }

    public Integer getWageDelayCount() {
        return wageDelayCount;
    }

    public void setWageDelayCount(Integer wageDelayCount) {
        this.wageDelayCount = wageDelayCount;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }
}