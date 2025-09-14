package com.coding24h.campus_job.entity;

import java.time.LocalDateTime;

public class AuthApplication {
    private Long authId;
    private Long userId;
    private String companyName;
    private String contactPerson;
    private String phone;
    private Integer creditScore;
    private String riskLevel; // LOW/MEDIUM/HIGH
    private Integer legalCaseCount;
    private Integer wageDelayCount;
    private String status; // PENDING/APPROVED/REJECTED
    private String businessLicense;
    private String taxCertificate;
    private LocalDateTime lastAuditTime;
    private String rejectReason;
    private String companySize;
    private String industry;
    private String headquarters;
    private String creditCode;
    private String companyDescription;

    // No-args constructor
    public AuthApplication() {
    }

    // All-args constructor
    public AuthApplication(Long authId, Long userId, String companyName, String contactPerson, String phone, Integer creditScore, String riskLevel, Integer legalCaseCount, Integer wageDelayCount, String status, String businessLicense, String taxCertificate, LocalDateTime lastAuditTime, String rejectReason, String companySize, String industry, String headquarters, String creditCode, String companyDescription) {
        this.authId = authId;
        this.userId = userId;
        this.companyName = companyName;
        this.contactPerson = contactPerson;
        this.phone = phone;
        this.creditScore = creditScore;
        this.riskLevel = riskLevel;
        this.legalCaseCount = legalCaseCount;
        this.wageDelayCount = wageDelayCount;
        this.status = status;
        this.businessLicense = businessLicense;
        this.taxCertificate = taxCertificate;
        this.lastAuditTime = lastAuditTime;
        this.rejectReason = rejectReason;
        this.companySize = companySize;
        this.industry = industry;
        this.headquarters = headquarters;
        this.creditCode = creditCode;
        this.companyDescription = companyDescription;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public LocalDateTime getLastAuditTime() {
        return lastAuditTime;
    }

    public void setLastAuditTime(LocalDateTime lastAuditTime) {
        this.lastAuditTime = lastAuditTime;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getCompanySize() {
        return companySize;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
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
}