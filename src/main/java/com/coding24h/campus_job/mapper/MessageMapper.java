package com.coding24h.campus_job.mapper;

import com.coding24h.campus_job.entity.Message;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface MessageMapper {
    @Insert("INSERT INTO message (conversation_id, sender_id, content, sent_at, is_read) " +
            "VALUES (#{conversationId}, #{senderId}, #{content}, #{sentAt}, #{isRead})")
    @Options(useGeneratedKeys = true, keyProperty = "messageId")
    void insertMessage(Message message);

    @Select("SELECT * FROM message WHERE conversation_id = #{conversationId} ORDER BY sent_at ASC")
    List<Message> findByConversationId(@Param("conversationId") Long conversationId);

    @Update("UPDATE message SET is_read = 1 WHERE conversation_id = #{conversationId} AND sender_id != #{userId}")
    void markMessagesAsRead(@Param("conversationId") Long conversationId, @Param("userId") Long userId);
}