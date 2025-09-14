package com.coding24h.campus_job.mapper;

import com.coding24h.campus_job.dto.AdminJobDTO;
import com.coding24h.campus_job.dto.JobResultDTO;
import com.coding24h.campus_job.dto.JobSearchDTO;
import com.coding24h.campus_job.dto.forDetail.JobDetailDTO;
import com.coding24h.campus_job.entity.Job;
import com.coding24h.campus_job.entity.JobReview;
import com.coding24h.campus_job.handler.JsonListTypeHandler;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper
public interface JobMapper {

    @Select("SELECT * FROM job WHERE job_id = #{jobId}")
    @Results({
            @Result(property = "jobId", column = "job_id"),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description"),
            @Result(property = "jobType", column = "job_type"),
            @Result(property = "riskLevel", column = "risk_level"),
            @Result(property = "wageGuarantee", column = "wage_guarantee"),
            @Result(property = "salaryRange", column = "salary_range"),
            @Result(property = "location", column = "location"),
            @Result(property = "companyUserId", column = "company_user_id"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "experienceRequirement", column = "experience_requirement"),
            @Result(property = "educationRequirement", column = "education_requirement"),
            @Result(property = "benefits", column = "benefits", typeHandler = JsonListTypeHandler.class)
    })
    Job findById(@Param("jobId") Long jobId);


    @Select("SELECT " +
            "j.job_id AS jobId, " +
            "ANY_VALUE(j.title) AS title, " +
            "ANY_VALUE(j.description) AS description, " +
            "ANY_VALUE(j.job_type) AS jobType, " +
            "ANY_VALUE(j.risk_level) AS riskLevel, " +
            "ANY_VALUE(j.wage_guarantee) AS wageGuarantee, " +
            "ANY_VALUE(j.salary_range) AS salaryRange, " +
            "ANY_VALUE(j.location) AS location, " +
            "ANY_VALUE(j.created_at) AS createdAt, " +
            "ANY_VALUE(j.company_user_id) AS companyUserId, " +
            "ANY_VALUE(u.company_name) AS companyName, " +
            "COALESCE(ANY_VALUE(ca.credit_score), 80) AS creditScore, " +
            "COALESCE(ROUND(AVG(jr.rating), 2), 0.0) AS averageRating, " +
            "COUNT(jr.review_id) AS reviewCount " +
            "FROM job j " +
            "INNER JOIN user u ON j.company_user_id = u.user_id " +
            "LEFT JOIN ( " +
            "   SELECT user_id, credit_score, " +
            "       ROW_NUMBER() OVER (PARTITION BY user_id ORDER BY auth_id DESC) AS rn " +
            "   FROM company_auth " +
            ") ca ON u.user_id = ca.user_id AND ca.rn = 1 " +
            "LEFT JOIN job_review jr ON j.job_id = jr.job_id " +
            "GROUP BY j.job_id " +
            "ORDER BY ANY_VALUE(j.created_at) DESC")
    List<JobResultDTO> getAllJobs();


    @Select("SELECT " +
            "j.job_id AS jobId, " +
            "j.title, " +
            "j.description, " +
            "j.job_type AS jobType, " +
            "j.risk_level AS riskLevel, " +
            "j.wage_guarantee AS wageGuarantee, " +
            "j.salary_range AS salaryRange, " +
            "j.location, " +
            "j.experience_requirement AS experienceRequirement, " +
            "j.education_requirement AS educationRequirement, " +
            "j.benefits, " +
            "j.company_user_id AS companyUserId, " +
            "u.company_name AS companyName, " +
            "u.financing_stage AS financingStage, " + // 添加融资阶段
            "u.industry, " + // 添加行业
            "u.headquarters, " + // 添加总部地点
            "u.company_size AS companySize, " + // 添加公司规模
            "COALESCE((SELECT AVG(rating) FROM job_review r WHERE r.job_id = j.job_id), 0.0) AS jobRating, " + // 职位评分
            "COALESCE((SELECT COUNT(*) FROM job_review r WHERE r.job_id = j.job_id), 0) AS jobReviewCount, " + // 职位评价数
            "COALESCE((SELECT AVG(rating) FROM job_review r WHERE r.job_id IN " +
            "   (SELECT job_id FROM job WHERE company_user_id = j.company_user_id)), 4.8) AS companyRating, " +
            "COALESCE((SELECT credit_score FROM company_auth WHERE user_id = u.user_id ORDER BY auth_id DESC LIMIT 1), 88) AS creditScore " +
            "FROM job j " +
            "JOIN user u ON j.company_user_id = u.user_id " +
            "WHERE j.job_id = #{jobId}")
    @Results({
            @Result(property = "benefits", column = "benefits", typeHandler = JsonListTypeHandler.class),
            @Result(property = "financingStage", column = "financingStage"),
            @Result(property = "industry", column = "industry"),
            @Result(property = "headquarters", column = "headquarters"),
            @Result(property = "companySize", column = "companySize"),
            @Result(property = "companyRating", column = "companyRating"),
            @Result(property = "creditScore", column = "creditScore"),
            @Result(property = "jobRating", column = "jobRating"),
            @Result(property = "jobReviewCount", column = "jobReviewCount")
    })
    JobDetailDTO selectJobDetailById(@Param("jobId") Long jobId);

    @Select("SELECT " +
            "job_id AS jobId, " +
            "title, " +
            "salary_range AS salaryRange, " +
            "(SELECT company_name FROM user WHERE user_id = company_user_id) AS companyName " +
            "FROM job " +
            "WHERE job_id != #{jobId} " +
            "ORDER BY RAND() " +
            "LIMIT #{limit}")
    List<JobDetailDTO.SimilarJobDTO> selectSimilarJobs(
            @Param("jobId") Long jobId,
            @Param("limit") int limit);


    @SelectProvider(type = JobSqlBuilder.class, method = "buildSearchSql")
    @Results({
            @Result(property = "jobId", column = "job_id"),
            @Result(property = "jobType", column = "job_type"),
            @Result(property = "riskLevel", column = "risk_level"),
            @Result(property = "wageGuarantee", column = "wage_guarantee"),
            @Result(property = "salaryRange", column = "salary_range"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "companyName", column = "company_name"),
            @Result(property = "companySize", column = "company_size"),
            @Result(property = "creditScore", column = "credit_score"),
            @Result(property = "averageRating", column = "average_rating"),
            @Result(property = "reviewCount", column = "review_count")
    })
    List<JobResultDTO> searchJobs(@Param("dto") JobSearchDTO dto, @Param("offset") int offset);

    @SelectProvider(type = JobSqlBuilder.class, method = "buildCountSql")
    long countJobs(@Param("dto") JobSearchDTO dto);

    class JobSqlBuilder {
        public String buildSearchSql(@Param("dto") JobSearchDTO dto, @Param("offset") int offset) {
            return new SQL() {{
                SELECT("ANY_VALUE(j.job_id) AS job_id, " +
                        "ANY_VALUE(j.title) AS title, " +
                        "ANY_VALUE(j.description) AS description, " +
                        "ANY_VALUE(j.job_type) AS job_type, " +
                        "ANY_VALUE(j.risk_level) AS risk_level, " +
                        "ANY_VALUE(j.wage_guarantee) AS wage_guarantee, " +
                        "ANY_VALUE(j.salary_range) AS salary_range, " +
                        "ANY_VALUE(j.location) AS location, " +
                        "ANY_VALUE(j.company_user_id) AS company_user_id, " +
                        "ANY_VALUE(j.created_at) AS created_at, " +
                        "ANY_VALUE(u.company_name) AS company_name, " +
                        "ANY_VALUE(u.company_size) AS company_size, " +
                        "ANY_VALUE(ca.credit_score) AS credit_score, " +
                        "AVG(jr.rating) AS average_rating, " +
                        "COUNT(jr.review_id) AS review_count");
                FROM("job j");
                JOIN("user u ON j.company_user_id = u.user_id");
                LEFT_OUTER_JOIN("company_auth ca ON u.user_id = ca.user_id");
                LEFT_OUTER_JOIN("job_review jr ON j.job_id = jr.job_id");
                buildWhereClause(this, dto);
                GROUP_BY("j.job_id");
                buildOrderByClause(this, dto);
            }}.toString() + " LIMIT #{dto.pageSize} OFFSET #{offset}";
        }

        public String buildCountSql(@Param("dto") JobSearchDTO dto) {
            return new SQL() {{
                SELECT("COUNT(DISTINCT j.job_id)");
                FROM("job j");
                JOIN("user u ON j.company_user_id = u.user_id");
                LEFT_OUTER_JOIN("company_auth ca ON u.user_id = ca.user_id");
                buildWhereClause(this, dto);
            }}.toString();
        }

        private void buildWhereClause(SQL sql, JobSearchDTO dto) {
            if (dto.getKeyword() != null && !dto.getKeyword().isEmpty()) {
                // 修复括号：确保左右括号数量匹配
                sql.WHERE("(j.title LIKE CONCAT('%', #{dto.keyword}, '%') OR " +
                        "u.company_name LIKE CONCAT('%', #{dto.keyword}, '%'))");
            }

            if (dto.getSalaryRange() != null) {
                switch (dto.getSalaryRange()) {
                    case "0-5000":
                        sql.WHERE("CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(j.salary_range, 'k', 1), '-', 1) AS UNSIGNED) < 5");
                        break;
                    case "5000-10000":
                        sql.WHERE("CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(j.salary_range, 'k', 1), '-', 1) AS UNSIGNED) BETWEEN 5 AND 10");
                        break;
                    case "10000-15000":
                        sql.WHERE("CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(j.salary_range, 'k', 1), '-', 1) AS UNSIGNED) BETWEEN 10 AND 15");
                        break;
                    case "15000-25000":
                        sql.WHERE("CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(j.salary_range, 'k', 1), '-', 1) AS UNSIGNED) BETWEEN 15 AND 25");
                        break;
                    case "25000-":
                        sql.WHERE("CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(j.salary_range, 'k', 1), '-', 1) AS UNSIGNED) >= 25");
                        break;
                }
            }

            if (dto.getJobTypes() != null && !dto.getJobTypes().isEmpty()) {
                String jobTypes = dto.getJobTypes().stream()
                        .map(Enum::name)
                        .map(s -> "'" + s + "'")
                        .collect(Collectors.joining(", "));
                sql.WHERE("j.job_type IN (" + jobTypes + ")");
            }

            if (dto.getCompanySizes() != null && !dto.getCompanySizes().isEmpty()) {
                String companySizes = dto.getCompanySizes().stream()
                        .map(s -> "'" + s + "'")
                        .collect(Collectors.joining(", "));
                sql.WHERE("u.company_size IN (" + companySizes + ")");
            }

            if (dto.getCreditScore() != null) {
                String[] creditRange = dto.getCreditScore().split("-");
                if (creditRange.length == 2) {
                    sql.WHERE("ca.credit_score BETWEEN " + creditRange[0] + " AND " + creditRange[1]);
                } else if (dto.getCreditScore().endsWith("-")) {
                    sql.WHERE("ca.credit_score >= " + dto.getCreditScore().replace("-", ""));
                }
            }
        }

        private void buildOrderByClause(SQL sql, JobSearchDTO dto) {
            switch (dto.getSortBy()) {
                case "salary":
                    sql.ORDER_BY("CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(salary_range, 'k', 1), '-', 1) AS UNSIGNED) DESC");
                    break;
                case "credit":
                    sql.ORDER_BY("credit_score DESC");
                    break;
                case "rating":
                    sql.ORDER_BY("average_rating DESC");
                    break;
                case "newest":
                default:
                    sql.ORDER_BY("created_at DESC");
                    break;
            }
        }
    }



    @Insert("INSERT INTO job (title, description, job_type, risk_level, wage_guarantee, salary_range, location, " +
            "company_user_id, created_at, experience_requirement, education_requirement, benefits) " +
            "VALUES (#{title}, #{description}, #{jobType}, #{riskLevel}, #{wageGuarantee}, #{salaryRange}, #{location}, " +
            "#{companyUserId}, #{createdAt}, #{experienceRequirement}, #{educationRequirement}, " +
            "#{benefits, typeHandler=com.coding24h.campus_job.handler.JsonListTypeHandler})")
    @Options(useGeneratedKeys = true, keyProperty = "jobId")
    int insertJob(Job job);



    // 新增方法
    @Update("UPDATE job SET title=#{title}, description=#{description}, job_type=#{jobType}, " +
            "risk_level=#{riskLevel}, wage_guarantee=#{wageGuarantee}, salary_range=#{salaryRange}, " +
            "location=#{location}, experience_requirement=#{experienceRequirement}, " +
            "education_requirement=#{educationRequirement}, " +
            "benefits=#{benefits, typeHandler=com.coding24h.campus_job.handler.JsonListTypeHandler} " +
            "WHERE job_id=#{jobId} AND company_user_id=#{companyUserId}")
    int updateJob(Job job);

    @Delete("DELETE FROM job WHERE job_id=#{jobId} AND company_user_id=#{companyUserId}")
    int deleteJob(@Param("jobId") Long jobId, @Param("companyUserId") Long companyUserId);

    @Select("SELECT * FROM job WHERE company_user_id=#{companyUserId} " +
            "ORDER BY created_at DESC LIMIT #{limit} OFFSET #{offset}")
    @Results({
            @Result(property = "jobId", column = "job_id"),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description"),
            @Result(property = "jobType", column = "job_type"),
            @Result(property = "riskLevel", column = "risk_level"),
            @Result(property = "wageGuarantee", column = "wage_guarantee"),
            @Result(property = "salaryRange", column = "salary_range"),
            @Result(property = "location", column = "location"),
            @Result(property = "companyUserId", column = "company_user_id"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "experienceRequirement", column = "experience_requirement"),
            @Result(property = "educationRequirement", column = "education_requirement"),
            @Result(property = "benefits", column = "benefits", typeHandler = JsonListTypeHandler.class)
    })
    List<Job> findJobsByCompany(@Param("companyUserId") Long companyUserId,
                                @Param("offset") int offset,
                                @Param("limit") int limit);

    @Select("SELECT COUNT(*) FROM job WHERE company_user_id=#{companyUserId}")
    int countJobsByCompany(@Param("companyUserId") Long companyUserId);


    @Select("SELECT r.*, j.title AS jobTitle " +
            "FROM job_review r " +
            "JOIN job j ON r.job_id = j.job_id " +
            "WHERE r.job_id = #{jobId}")
    List<JobReview> findReviewsWithJobTitle(@Param("jobId") Long jobId);




    @SelectProvider(type = AdminJobSqlBuilder.class, method = "buildAdminJobsSql")
    @Results({
            @Result(property = "jobId", column = "job_id"),
            @Result(property = "title", column = "title"),
            @Result(property = "jobType", column = "job_type"), // 映射为字符串
            @Result(property = "riskLevel", column = "risk_level"), // 映射为字符串
            @Result(property = "salaryRange", column = "salary_range"),
            @Result(property = "companyName", column = "company_name"),
            @Result(property = "companySize", column = "company_size"),
            @Result(property = "createdAt", column = "created_at")
    })
    List<AdminJobDTO> findAdminJobs(
            @Param("offset") int offset,
            @Param("limit") int limit,
            @Param("filters") Map<String, String> filters
    );
    @SelectProvider(type = AdminJobSqlBuilder.class, method = "buildAdminJobsCountSql")
    long countAdminJobs(@Param("filters") Map<String, String> filters);

    @Select("SELECT COUNT(*) FROM job")
    long countAllJobs();

    @Select("SELECT COUNT(*) FROM job WHERE risk_level = #{riskLevel}")
    long countJobsByRiskLevel(@Param("riskLevel") String riskLevel);

    @Delete("DELETE FROM job WHERE job_id = #{jobId}")
    void deleteJobAdmin(@Param("jobId") Long jobId);

    class AdminJobSqlBuilder {
        public String buildAdminJobsSql(Map<String, Object> params) {
            int offset = (int) params.get("offset");
            int limit = (int) params.get("limit");
            Map<String, String> filters = (Map<String, String>) params.get("filters");

            return new SQL() {{
                SELECT("j.job_id, j.title, j.job_type, j.risk_level, j.salary_range, " +
                        "u.company_name, u.company_size, j.created_at");
                FROM("job j");
                INNER_JOIN("user u ON j.company_user_id = u.user_id");

                if (filters != null) {
                    if (filters.containsKey("search") && !filters.get("search").isEmpty()) {
                        WHERE("(j.title LIKE CONCAT('%', #{filters.search}, '%') " +
                                "OR u.company_name LIKE CONCAT('%', #{filters.search}, '%'))");
                    }
                    if (filters.containsKey("jobType") && !filters.get("jobType").isEmpty()) {
                        WHERE("j.job_type = #{filters.jobType}");
                    }
                    if (filters.containsKey("riskLevel") && !filters.get("riskLevel").isEmpty()) {
                        WHERE("j.risk_level = #{filters.riskLevel}");
                    }
                }

                ORDER_BY("j.created_at DESC");
            }}.toString() + " LIMIT #{limit} OFFSET #{offset}";
        }

        public String buildAdminJobsCountSql(Map<String, Object> params) {
            Map<String, String> filters = (Map<String, String>) params.get("filters");

            return new SQL() {{
                SELECT("COUNT(*)");
                FROM("job j");
                INNER_JOIN("user u ON j.company_user_id = u.user_id");

                if (filters != null) {
                    if (filters.containsKey("search") && !filters.get("search").isEmpty()) {
                        WHERE("(j.title LIKE CONCAT('%', #{filters.search}, '%') " +
                                "OR u.company_name LIKE CONCAT('%', #{filters.search}, '%'))");
                    }
                    if (filters.containsKey("jobType") && !filters.get("jobType").isEmpty()) {
                        WHERE("j.job_type = #{filters.jobType}");
                    }
                    if (filters.containsKey("riskLevel") && !filters.get("riskLevel").isEmpty()) {
                        WHERE("j.risk_level = #{filters.riskLevel}");
                    }
                }
            }}.toString();
        }
    }

    @Select("SELECT COUNT(*) FROM job_review")
    long countAllReviews();

    @Select("SELECT r.*, j.title AS job_title, " +
            "u.company_name, j.location, j.salary_range, " +
            "su.username AS username " + // 新增学生用户名
            "FROM job_review r " +
            "JOIN job j ON r.job_id = j.job_id " +
            "JOIN user u ON j.company_user_id = u.user_id " +
            "JOIN user su ON r.user_id = su.user_id " + // 新增关联学生用户表
            "ORDER BY r.created_at DESC " +
            "LIMIT #{size} OFFSET #{offset}")
    @Results({
            @Result(property = "jobId", column = "job_id"),
            @Result(property = "reviewText", column = "review_text"), // 确保映射这个字段
            @Result(property = "jobTitle", column = "job_title"),
            @Result(property = "companyName", column = "company_name"),
            @Result(property = "location", column = "location"),
            @Result(property = "salaryRange", column = "salary_range"),
            @Result(property = "username", column = "username")
    })
    List<JobReview> findReviewsWithJobInfo(
            @Param("offset") int offset,
            @Param("size") int size);

    @Delete("DELETE FROM job_review WHERE job_id = #{jobId}")
    void deleteReviewsByJobId(@Param("jobId") Long jobId);
}

