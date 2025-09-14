package com.coding24h.campus_job.mapper;

import com.coding24h.campus_job.entity.Conversation;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ConversationMapper {



    @Update("UPDATE conversation SET last_message = #{lastMessage}, updated_at = #{updatedAt} WHERE conversation_id = #{conversationId}")
    void updateConversation(Conversation conversation);

    @Select("SELECT * FROM conversation WHERE job_id = #{jobId} " +
            "AND (user_id = #{userId} OR company_user_id = #{userId})")
    Conversation findByJobIdAndUserId(@Param("jobId") Long jobId,
                                      @Param("userId") Long userId);


    // 避免返回多个结果
    @Select("SELECT user_id FROM conversation " +
            "WHERE job_id = #{jobId} AND company_user_id = #{companyUserId} " +
            "LIMIT 1")
    Long findStudentUserIdByJob(@Param("jobId") Long jobId,
                                @Param("companyUserId") Long companyUserId);


    @Select("SELECT * FROM conversation WHERE job_id = #{jobId}")
    List<Conversation> findByJobId(@Param("jobId") Long jobId);

    @Select("SELECT * FROM conversation WHERE user_id = #{userId} OR company_user_id = #{userId}")
    List<Conversation> findByUserId(@Param("userId") Long userId);

    @Select("SELECT * FROM conversation WHERE conversation_id = #{conversationId}")
    Conversation findById(@Param("conversationId") Long conversationId);

    @Select("SELECT * FROM conversation WHERE conversation_id = #{conversationId}")
    @Results({
            @Result(property = "conversationId", column = "conversation_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "companyUserId", column = "company_user_id"),
            @Result(property = "jobId", column = "job_id"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at"),
            @Result(property = "lastMessage", column = "last_message"),
            @Result(property = "studentName", column = "student_name")
    })
    Conversation getConversationById(@Param("conversationId") Long conversationId);


    // 添加以下方法
    @Select("SELECT * FROM conversation WHERE user_id = #{userId} AND company_user_id = #{companyUserId} AND job_id = #{jobId} ORDER BY conversation_id DESC LIMIT 1")
    Conversation findByUserAndCompanyAndJob(
            @Param("userId") Long userId,
            @Param("companyUserId") Long companyUserId,
            @Param("jobId") Long jobId);

    @Insert("INSERT INTO conversation (user_id, company_user_id, job_id, created_at, updated_at, student_name) " +
            "VALUES (#{userId}, #{companyUserId}, #{jobId}, #{createdAt}, #{updatedAt}, #{studentName})")
    @Options(useGeneratedKeys = true, keyProperty = "conversationId")
    void insertConversation(Conversation conversation);
}