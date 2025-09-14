package com.coding24h.campus_job.dto.auth;

import java.time.LocalDateTime;

public class AuthStatusResponse {
    private String status;
    private Integer creditScore;
    private String rejectReason;
    private LocalDateTime lastAuditTime;

    // No-args constructor
    public AuthStatusResponse() {
    }

    // All-args constructor
    public AuthStatusResponse(String status, Integer creditScore, String rejectReason, LocalDateTime lastAuditTime) {
        this.status = status;
        this.creditScore = creditScore;
        this.rejectReason = rejectReason;
        this.lastAuditTime = lastAuditTime;
    }

    // Getters and Setters
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

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public LocalDateTime getLastAuditTime() {
        return lastAuditTime;
    }

    public void setLastAuditTime(LocalDateTime lastAuditTime) {
        this.lastAuditTime = lastAuditTime;
    }
}