package com.coding24h.campus_job.service;

import com.coding24h.campus_job.dto.PageResult;
import com.coding24h.campus_job.dto.auth.AuthRequest;
import com.coding24h.campus_job.dto.auth.AuthReviewRequest;
import com.coding24h.campus_job.dto.auth.AuthStatusResponse;
import com.coding24h.campus_job.dto.auth.AuthSubmitResponse;
import com.coding24h.campus_job.entity.AuthApplication;
import com.coding24h.campus_job.entity.CompanyAuth;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface CompanyAuthService {

    AuthStatusResponse getAuthStatus(Long userId);

    AuthSubmitResponse submitAuth(AuthRequest request, MultipartFile businessLicense,
                                  MultipartFile taxCertificate);

    // 获取认证统计信息
    Map<String, Long> getAuthStats();


   List<AuthApplication> findApplications(int offset, int size, String search, String status, String scoreRange);


  int countApplications(String search, String status, String scoreRange);


    // 获取认证详情
    AuthApplication getAuthDetail(Long authId);

    // 删除认证
    void deleteAuth(Long authId);

    // 通过认证
    void approveAuth(Long authId);

    // 拒绝认证
    void rejectAuth(Long authId, String reason);
}
