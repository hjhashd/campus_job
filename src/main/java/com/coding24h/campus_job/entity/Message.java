package com.coding24h.campus_job.entity;

import java.time.LocalDateTime;

public class Message {
    private Long messageId;
    private Long conversationId;
    private Long senderId;          // 发送者ID
    private String content;
    private LocalDateTime sentAt;
    private Boolean isRead;         // 是否已读

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    // 修改这里
    public Boolean isRead() {
        return isRead;
    }

    public void setIsRead(Boolean read) {
        isRead = read;
    }
}