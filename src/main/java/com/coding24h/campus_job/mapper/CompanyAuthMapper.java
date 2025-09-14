package com.coding24h.campus_job.mapper;

import com.coding24h.campus_job.entity.AuthApplication;
import com.coding24h.campus_job.entity.CompanyAuth;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CompanyAuthMapper {

    @Insert("INSERT INTO company_auth (user_id, credit_code, company_description, " +
            "business_license, tax_certificate, status, credit_score, " +
            "last_audit_time, auditor_id, reject_reason, legal_case_count, wage_delay_count) " +
            "VALUES (#{userId}, #{creditCode}, #{companyDescription}, #{businessLicense}, " +
            "#{taxCertificate}, #{status}, #{creditScore}, #{lastAuditTime}, " +
            "#{auditorId}, #{rejectReason}, #{legalCaseCount}, #{wageDelayCount})")
    @Options(useGeneratedKeys = true, keyProperty = "authId")
    void insert(CompanyAuth auth);

    @Update("UPDATE company_auth SET credit_code = #{creditCode}, " +
            "company_description = #{companyDescription}, " +
            "business_license = #{businessLicense}, " +
            "tax_certificate = #{taxCertificate}, status = #{status}, " +
            "credit_score = #{creditScore},last_audit_time = #{lastAuditTime, jdbcType=TIMESTAMP}, " +
            "auditor_id = #{auditorId}, reject_reason = #{rejectReason}, " +
            "legal_case_count = #{legalCaseCount}, wage_delay_count = #{wageDelayCount} " +
            "WHERE auth_id = #{authId}")
    void update(CompanyAuth auth);

    @Select("SELECT * FROM company_auth WHERE user_id = #{userId}")
    CompanyAuth findByUserId(@Param("userId") Long userId);

    @Select("SELECT COUNT(*) FROM company_auth")
    long countTotalAuth();

    @Select("SELECT COUNT(*) FROM company_auth WHERE status = #{status}")
    long countAuthByStatus(@Param("status") String status);

    @Select({
            "<script>",
            "SELECT ",
            "ca.auth_id AS authId, ",
            "ca.user_id AS userId, ",
            "u.company_name AS companyName, ",
            "u.contact_person AS contactPerson, ",
            "u.phone, ",
            "ca.credit_score AS creditScore, ",
            "CASE ",
            "   WHEN ca.credit_score &gt;= 85 THEN 'LOW' ", // 转义 >=
            "   WHEN ca.credit_score &gt;= 70 THEN 'MEDIUM' ", // 转义 >=
            "   ELSE 'HIGH' ",
            "END AS riskLevel, ",
            "ca.legal_case_count AS legalCaseCount, ",
            "ca.wage_delay_count AS wageDelayCount, ",
            "ca.status, ",
            "ca.business_license AS businessLicense, ",
            "ca.tax_certificate AS taxCertificate, ",
            "ca.last_audit_time AS lastAuditTime, ",
            "ca.reject_reason AS rejectReason, ",
            "u.company_size AS companySize, ",
            "u.industry, ",
            "u.headquarters ",
            "FROM company_auth ca ",
            "JOIN user u ON ca.user_id = u.user_id ",
            "WHERE u.identity_type = 'COMPANY' ",
            "<if test='search != null and search != \"\"'>",
            "   AND (u.company_name LIKE CONCAT('%', #{search}, '%') ",
            "   OR u.contact_person LIKE CONCAT('%', #{search}, '%') ",
            "   OR u.phone LIKE CONCAT('%', #{search}, '%')) ",
            "</if>",
            "<if test='status != null and status != \"\"'>",
            "   AND ca.status = #{status} ",
            "</if>",
            "<if test='scoreRange != null and scoreRange != \"\"'>",
            "   <choose>",
            "       <when test='scoreRange == \"high\"'>",
            "           AND ca.credit_score &gt;= 80 ", // 转义 >=
            "       </when>",
            "       <when test='scoreRange == \"medium\"'>",
            "           AND ca.credit_score BETWEEN 60 AND 79 ",
            "       </when>",
            "       <when test='scoreRange == \"low\"'>",
            "           AND ca.credit_score &lt; 60 ", // 转义 <
            "       </when>",
            "   </choose>",
            "</if>",
            "ORDER BY ca.last_audit_time DESC ",
            "LIMIT #{limit} OFFSET #{offset}",
            "</script>"
    })
    List<AuthApplication> findApplications(
            @Param("offset") int offset,
            @Param("limit") int limit,
            @Param("search") String search,
            @Param("status") String status,
            @Param("scoreRange") String scoreRange);

    @Select({
            "<script>",
            "SELECT COUNT(*) ",
            "FROM company_auth ca ",
            "JOIN user u ON ca.user_id = u.user_id ",
            "WHERE u.identity_type = 'COMPANY' ",
            "<if test='search != null and search != \"\"'>",
            "   AND (u.company_name LIKE CONCAT('%', #{search}, '%') ",
            "   OR u.contact_person LIKE CONCAT('%', #{search}, '%') ",
            "   OR u.phone LIKE CONCAT('%', #{search}, '%')) ",
            "</if>",
            "<if test='status != null and status != \"\"'>",
            "   AND ca.status = #{status} ",
            "</if>",
            "<if test='scoreRange != null and scoreRange != \"\"'>",
            "   <choose>",
            "       <when test='scoreRange == \"high\"'>",
            "           AND ca.credit_score &gt;= 80 ", // 转义 >=
            "       </when>",
            "       <when test='scoreRange == \"medium\"'>",
            "           AND ca.credit_score BETWEEN 60 AND 79 ",
            "       </when>",
            "       <when test='scoreRange == \"low\"'>",
            "           AND ca.credit_score &lt; 60 ", // 转义 <
            "       </when>",
            "   </choose>",
            "</if>",
            "</script>"
    })
    int countApplications(
            @Param("search") String search,
            @Param("status") String status,
            @Param("scoreRange") String scoreRange);

    @Select("SELECT " +
            "ca.auth_id AS authId, " +
            "ca.user_id AS userId, " +
            "u.company_name AS companyName, " +
            "u.contact_person AS contactPerson, " +
            "u.phone, " +
            "ca.credit_score AS creditScore, " +
            "CASE " +
            "   WHEN ca.credit_score >= 85 THEN 'LOW' " +
            "   WHEN ca.credit_score >= 70 THEN 'MEDIUM' " +
            "   ELSE 'HIGH' " +
            "END AS riskLevel, " +
            "ca.legal_case_count AS legalCaseCount, " +
            "ca.wage_delay_count AS wageDelayCount, " +
            "ca.status, " +
            "ca.business_license AS businessLicense, " +
            "ca.tax_certificate AS taxCertificate, " +
            "ca.last_audit_time AS lastAuditTime, " +
            "ca.reject_reason AS rejectReason, " +
            "u.company_size AS companySize, " +
            "u.industry, " +
            "u.headquarters, " +
            "ca.credit_code AS creditCode, " +
            "ca.company_description AS companyDescription " +
            "FROM company_auth ca " +
            "JOIN user u ON ca.user_id = u.user_id " +
            "WHERE ca.auth_id = #{authId}")
    AuthApplication findAuthDetailById(@Param("authId") Long authId);

    @Delete("DELETE FROM company_auth WHERE auth_id = #{authId}")
    void deleteAuth(@Param("authId") Long authId);

    @Update("UPDATE company_auth SET status = #{status}, " +
            "reject_reason = #{reason}, " +
            "last_audit_time = NOW() " +  // 更新审核时间
            "WHERE auth_id = #{authId}")
    void updateStatus(@Param("authId") Long authId,
                      @Param("status") String status,
                      @Param("reason") String reason);



}