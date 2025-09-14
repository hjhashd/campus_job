package com.coding24h.campus_job.dto.auth;

public class AuthReviewRequest {
    private Long authId;
    private String status;
    private Integer creditScore;
    private String rejectReason;
    private Long auditorId;

    // No-args constructor
    public AuthReviewRequest() {
    }

    // All-args constructor
    public AuthReviewRequest(Long authId, String status, Integer creditScore, String rejectReason, Long auditorId) {
        this.authId = authId;
        this.status = status;
        this.creditScore = creditScore;
        this.rejectReason = rejectReason;
        this.auditorId = auditorId;
    }

    // Getters and Setters
    public Long getAuthId() {
        return authId;
    }

    public void setAuthId(Long authId) {
        this.authId = authId;
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

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public Long getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Long auditorId) {
        this.auditorId = auditorId;
    }
}