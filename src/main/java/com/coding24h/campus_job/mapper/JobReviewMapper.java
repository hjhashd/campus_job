package com.coding24h.campus_job.mapper;

import com.coding24h.campus_job.entity.JobReview;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface JobReviewMapper {

    @Select("SELECT r.*, j.title AS job_title " +
            "FROM job_review r " +
            "JOIN job j ON r.job_id = j.job_id " +
            "WHERE r.user_id = #{userId}")
    @Results({
            @Result(property = "reviewId", column = "review_id"),
            @Result(property = "jobId", column = "job_id"),
            @Result(property = "rating", column = "rating"),
            @Result(property = "wagePromptness", column = "wage_promptness"),
            @Result(property = "envAuthenticity", column = "env_authenticity"),
            @Result(property = "reviewText", column = "review_text"),
            @Result(property = "verified", column = "verified"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "jobTitle", column = "job_title") // 添加职位标题
    })
    List<JobReview> findByUserId(Long userId);
}