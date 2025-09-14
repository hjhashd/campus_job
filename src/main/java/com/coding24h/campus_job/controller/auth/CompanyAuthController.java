package com.coding24h.campus_job.controller.auth;

import com.coding24h.campus_job.dto.auth.AuthRequest;
import com.coding24h.campus_job.dto.auth.AuthStatusResponse;
import com.coding24h.campus_job.dto.auth.AuthSubmitResponse;
import com.coding24h.campus_job.service.CompanyAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/company/auth")
public class CompanyAuthController {

    @Autowired
    private CompanyAuthService authService;

    @GetMapping("/status")
    public ResponseEntity<AuthStatusResponse> getAuthStatus(
            @RequestParam("userId") Long userId) {
        AuthStatusResponse response = authService.getAuthStatus(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/submit")
    public ResponseEntity<AuthSubmitResponse> submitAuth(
            @RequestParam("userId") Long userId,
            @RequestParam("companyName") String companyName,
            @RequestParam("creditCode") String creditCode,
            @RequestParam("companySize") String companySize,
            @RequestParam("industry") String industry,
            @RequestParam("description") String description,
            @RequestParam("businessLicense") MultipartFile businessLicense,
            @RequestParam("taxCertificate") MultipartFile taxCertificate,
            // 新增的两个参数
            @RequestParam("financingStage") String financingStage,
            @RequestParam("headquarters") String headquarters) {

        AuthRequest request = new AuthRequest();
        request.setUserId(userId);
        request.setCompanyName(companyName);
        request.setCreditCode(creditCode);
        request.setCompanySize(companySize);
        request.setIndustry(industry);
        request.setDescription(description);
        // 设置新增的两个字段
        request.setFinancingStage(financingStage);
        request.setHeadquarters(headquarters);


        // 调用服务层处理认证
        AuthSubmitResponse response = authService.submitAuth(
                request, businessLicense, taxCertificate);

        // 根据服务层返回的状态创建响应对象
        if ("APPROVED".equals(response.getStatus())) {
            return ResponseEntity.ok(new AuthSubmitResponse(
                    true,
                    "企业认证已通过审核！您现在可以使用所有企业功能。",
                    "APPROVED",
                    response.getCreditScore()));
        } else if ("REJECTED".equals(response.getStatus())) {
            return ResponseEntity.ok(new AuthSubmitResponse(
                    false,
                    "认证未通过，请查看拒绝原因并修改资料后重新提交。",
                    "REJECTED",
                    response.getRejectReason()));
        } else {
            return ResponseEntity.ok(new AuthSubmitResponse(
                    true,
                    "认证资料已成功提交！审核通常需要1-3个工作日。",
                    "PENDING"));
        }
    }
}