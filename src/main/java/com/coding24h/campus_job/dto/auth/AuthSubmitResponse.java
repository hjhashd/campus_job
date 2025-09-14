package com.coding24h.campus_job.dto.auth;

public class AuthSubmitResponse {
    private boolean success;
    private String message;
    private String status;
    private String rejectReason; // 添加拒绝原因字段
    private Integer creditScore; // 添加信用分字段

    // 构造方法
    public AuthSubmitResponse(boolean success, String message, String status) {
        this.success = success;
        this.message = message;
        this.status = status;
    }

    // 带拒绝原因的构造方法
    public AuthSubmitResponse(boolean success, String message, String status, String rejectReason) {
        this.success = success;
        this.message = message;
        this.status = status;
        this.rejectReason = rejectReason;
    }

    // 带信用分的构造方法
    public AuthSubmitResponse(boolean success, String message, String status, Integer creditScore) {
        this.success = success;
        this.message = message;
        this.status = status;
        this.creditScore = creditScore;
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public Integer getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }
}