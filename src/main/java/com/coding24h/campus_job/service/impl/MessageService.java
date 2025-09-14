package com.coding24h.campus_job.service.impl;

import com.coding24h.campus_job.entity.Conversation;
import com.coding24h.campus_job.entity.Job;
import com.coding24h.campus_job.entity.Message;
import com.coding24h.campus_job.mapper.ConversationMapper;
import com.coding24h.campus_job.mapper.JobMapper;
import com.coding24h.campus_job.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private ConversationMapper conversationMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private JobMapper jobMapper; // 假设您有JobMapper

    @Transactional
    public void sendMessageByConversationId(Long senderId, Long conversationId, String content) {
        Conversation conversation = conversationMapper.findById(conversationId);

        // 更新对话最后消息和时间
        conversation.setLastMessage(content);
        conversation.setUpdatedAt(LocalDateTime.now());
        conversationMapper.updateConversation(conversation);

        // 保存消息
        Message message = new Message();
        message.setConversationId(conversationId);
        message.setSenderId(senderId);
        message.setContent(content);
        message.setSentAt(LocalDateTime.now());
        message.setIsRead(false);
        messageMapper.insertMessage(message);

        // 标记对方消息为已读
        messageMapper.markMessagesAsRead(conversationId, senderId);
    }

    public List<Conversation> getConversationsForJob(Long jobId) {
        return conversationMapper.findByJobId(jobId);
    }

    public List<Conversation> getConversationsForUser(Long userId) {
        return conversationMapper.findByUserId(userId);
    }

    public Conversation getConversationById(Long conversationId) {
        return conversationMapper.getConversationById(conversationId);
    }

    public Long createConversation(Conversation conversation) {
        // 检查是否已存在对话
        Conversation existing = conversationMapper.findByUserAndCompanyAndJob(
                conversation.getUserId(),
                conversation.getCompanyUserId(),
                conversation.getJobId()
        );

        if (existing != null) {
            return existing.getConversationId();
        }

        // 创建新对话
        conversationMapper.insertConversation(conversation);
        return conversation.getConversationId();
    }

    // 更新获取消息方法
    public List<Message> getMessages(Long conversationId, Long currentUserId) {
        // 标记对方消息为已读
        messageMapper.markMessagesAsRead(conversationId, currentUserId);

        // 获取所有消息
        return messageMapper.findByConversationId(conversationId);
    }
}