package com.coding24h.campus_job.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

public class User {
    private Long userId;

    private String identityType;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String avatar = "default-avatar.png";
    private String school;
    private String major;
    private String education;
    private String companyName;
    private String companySize;
    private String contactPerson;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String financingStage;
    private String industry;
    private String headquarters;
    private String creditCode;
    private String role;
    private Integer status = 1; // 默认值设为 1 (ACTIVE)

    private String statusText;



    public static final Integer STATUS_ACTIVE = 1;
    public static final Integer STATUS_INACTIVE = 0;


    // No-args constructor
    public User() {
    }

    // All-args constructor
    public User(Long userId, String identityType, String username, String password, String email, String phone, String avatar, String school, String major, String education, String companyName, String companySize, String contactPerson, LocalDateTime createdAt, LocalDateTime updatedAt, String financingStage, String industry, String headquarters, String creditCode, String role,Integer status,String statusText) {
        this.userId = userId;
        this.identityType = identityType;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.avatar = avatar;
        this.school = school;
        this.major = major;
        this.education = education;
        this.companyName = companyName;
        this.companySize = companySize;
        this.contactPerson = contactPerson;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.financingStage = financingStage;
        this.industry = industry;
        this.headquarters = headquarters;
        this.creditCode = creditCode;
        this.role = role;
        this.status = status;
        this.statusText = statusText;
    }

    // 添加辅助方法判断状态
    public boolean isActive() {
        return status != null && status == 1; // 假设 1=ACTIVE
    }


    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public Integer getStatus() {
        return status;
    }

    public String getStatusText() {
        return status == 1 ? "活跃" : "非活跃";
    }

    public void setStatus(Integer   status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getIdentityType() {
        return identityType;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
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

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
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

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}