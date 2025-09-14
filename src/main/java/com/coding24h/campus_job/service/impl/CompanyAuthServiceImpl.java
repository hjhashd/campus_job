package com.coding24h.campus_job.service.impl;

import com.coding24h.campus_job.dto.auth.AuthRequest;
import com.coding24h.campus_job.dto.auth.AuthReviewRequest;
import com.coding24h.campus_job.dto.auth.AuthStatusResponse;
import com.coding24h.campus_job.dto.auth.AuthSubmitResponse;
import com.coding24h.campus_job.entity.AuthApplication;
import com.coding24h.campus_job.entity.CompanyAuth;
import com.coding24h.campus_job.entity.User;
import com.coding24h.campus_job.exception.FileStorageException;
import com.coding24h.campus_job.exception.ResourceNotFoundException;
import com.coding24h.campus_job.mapper.CompanyAuthMapper;
import com.coding24h.campus_job.mapper.UserMapper;
import com.coding24h.campus_job.service.CompanyAuthService;
import com.coding24h.campus_job.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CompanyAuthServiceImpl implements CompanyAuthService {

    @Autowired
    private CompanyAuthMapper authMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FileStorageService fileStorageService;

    public AuthStatusResponse getAuthStatus(Long userId) {

        CompanyAuth auth = authMapper.findByUserId(userId);

        // 没有认证记录的情况
        if (auth == null) {
            return new AuthStatusResponse("NOT_SUBMITTED", null, null, null);
        }

        return new AuthStatusResponse(
                auth.getStatus(),
                auth.getCreditScore(),
                auth.getRejectReason(),
                auth.getLastAuditTime()
        );
    }

    @Transactional
    public AuthSubmitResponse submitAuth(AuthRequest request, MultipartFile businessLicense,
                                         MultipartFile taxCertificate) {

        // 1. 获取用户
        User user = userMapper.findById(request.getUserId());
        if (user == null) {
            throw new ResourceNotFoundException("用户不存在");
        }

        // 打印更新前的值
        System.out.println("===== 更新前用户信息 =====");
        System.out.println("用户ID: " + user.getUserId());
        System.out.println("公司名称: " + user.getCompanyName());
        System.out.println("统一信用代码: " + user.getCreditCode());
        System.out.println("更新时间: " + user.getUpdatedAt());

        // 2. 设置新值
        user.setCompanyName(request.getCompanyName());
        user.setCreditCode(request.getCreditCode());
        user.setCompanySize(request.getCompanySize());
        user.setIndustry(request.getIndustry());
        user.setUpdatedAt(LocalDateTime.now());
        user.setFinancingStage(request.getFinancingStage());
        user.setHeadquarters(request.getHeadquarters());

        // 3. 执行更新
        int affectedRows = userMapper.updateCompanyInfo(user);
        System.out.println("受影响行数: " + affectedRows);

        // 4. 重新查询验证更新
        User updatedUser = userMapper.findById(request.getUserId());
        System.out.println("===== 更新后用户信息 =====");
        System.out.println("公司名称: " + updatedUser.getCompanyName());
        System.out.println("统一信用代码: " + updatedUser.getCreditCode());
        System.out.println("更新时间: " + updatedUser.getUpdatedAt());

        // ... 后续代码 ...


        // 2. 获取现有的认证记录（如果存在）
        CompanyAuth auth;
        boolean isNew = false; // 标记是否为新记录

        // 直接获取认证记录对象
        CompanyAuth existingAuth = authMapper.findByUserId(request.getUserId());

        if (existingAuth != null) {
            // 已有认证记录 - 更新它
            auth = existingAuth;
        } else {
            // 没有认证记录 - 创建新对象
            auth = new CompanyAuth();
            auth.setUserId(request.getUserId());
            auth.setStatus("PENDING");
            auth.setCreditScore(80); // 默认信用分
            auth.setLegalCaseCount(0);
            auth.setWageDelayCount(0);
            isNew = true; // 标记为新记录
        }

        // 3. 更新公共字段
        auth.setCreditCode(request.getCreditCode());
        auth.setCompanyDescription(request.getDescription());

        // 4. 处理文件上传（修复existingAuth问题）
        String licensePath = auth.getBusinessLicense(); // 如果已有，默认为当前文件
        if (businessLicense != null && !businessLicense.isEmpty()) {
            // 如果有新文件上传，则存储新文件
            licensePath = fileStorageService.storeFile(businessLicense, "licenses");
        } else if (isNew) {
            // 如果是新认证且没有上传营业执照
            throw new FileStorageException("新认证需要上传营业执照");
        }

        String taxPath = auth.getTaxCertificate(); // 如果已有，默认为当前文件
        if (taxCertificate != null && !taxCertificate.isEmpty()) {
            // 如果有新文件上传，则存储新文件
            taxPath = fileStorageService.storeFile(taxCertificate, "tax_certs");
        } else if (isNew) {
            // 如果是新认证且没有上传税务登记证
            throw new FileStorageException("新认证需要上传税务登记证");
        }

        // 5. 设置文件路径
        auth.setBusinessLicense(licensePath);
        auth.setTaxCertificate(taxPath);

        // 6. 保存或更新认证记录
        if (isNew) {
            // 新记录需要手动设置其他字段
            authMapper.insert(auth);
        } else {
            // 更新记录时需要清除可能的拒绝原因
            auth.setRejectReason(null);
            authMapper.update(auth);
        }

        // 7. 返回响应
        return new AuthSubmitResponse(true, "认证资料已提交", "PENDING");
    }



    @Override
    public Map<String, Long> getAuthStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("total", authMapper.countTotalAuth());
        stats.put("pending", authMapper.countAuthByStatus("PENDING"));
        stats.put("approved", authMapper.countAuthByStatus("APPROVED"));
        stats.put("rejected", authMapper.countAuthByStatus("REJECTED"));
        return stats;
    }

    @Override
    public List<AuthApplication> findApplications(int offset, int size, String search, String status, String scoreRange) {
        return authMapper.findApplications(offset, size, search, status, scoreRange);
    }

    @Override
    public int countApplications(String search, String status, String scoreRange) {
        return authMapper.countApplications(search, status, scoreRange);
    }

    @Override
    public AuthApplication getAuthDetail(Long authId) {
        return authMapper.findAuthDetailById(authId);
    }

    @Override
    public void deleteAuth(Long authId) {
        authMapper.deleteAuth(authId);
    }

    @Override
    public void approveAuth(Long authId) {
        // 更新状态为APPROVED并清除拒绝原因
        authMapper.updateStatus(authId, "APPROVED", null);
    }

    @Override
    public void rejectAuth(Long authId, String reason) {
        // 更新状态为REJECTED并记录原因
        authMapper.updateStatus(authId, "REJECTED", reason);
    }

}
