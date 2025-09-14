package com.coding24h.campus_job.controller;

import com.coding24h.campus_job.entity.*;
import com.coding24h.campus_job.service.JobService;
import com.coding24h.campus_job.service.impl.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller; // 改为Controller
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller // 改为Controller而非RestController
@RequestMapping("/messages") // 统一路径前缀
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private JobService jobService;

    @PostMapping("/send")
    public String sendMessage(
            @AuthenticationPrincipal CustomUserDetails currentUser,
            @RequestParam Long conversationId,
            @RequestParam String content) {

        messageService.sendMessageByConversationId(
                currentUser.getUserId(),
                conversationId,
                content
        );

        return "redirect:/messages/chat?conversationId=" + conversationId;
    }

    // 新增的创建对话方法
    @PostMapping("/create")
    public String createConversation(
            @AuthenticationPrincipal CustomUserDetails currentUser,
            @RequestParam Long jobId,
            @RequestParam Long companyUserId) {

        // 创建新对话
        Conversation conversation = new Conversation();
        conversation.setUserId(currentUser.getUserId());
        conversation.setCompanyUserId(companyUserId);
        conversation.setJobId(jobId);
        conversation.setCreatedAt(LocalDateTime.now());
        conversation.setStudentName(currentUser.getUsername()); // 存储学生姓名

        Long conversationId = messageService.createConversation(conversation);

        return "redirect:/messages/chat?conversationId=" + conversationId;
    }

    // 修改现有的getChatPage方法，添加未读消息处理
    @GetMapping("/chat")
    public String getChatPage(
            @AuthenticationPrincipal CustomUserDetails currentUser,
            @RequestParam("conversationId") Long conversationId,
            Model model) {

        Conversation conversation = messageService.getConversationById(conversationId);

        // 权限校验 - 确保当前用户是对话参与者
        if (!conversation.getUserId().equals(currentUser.getUserId()) &&
                !conversation.getCompanyUserId().equals(currentUser.getUserId())) {
            throw new AccessDeniedException("无权访问此对话");
        }

        // 获取消息并标记为已读
        List<Message> messages = messageService.getMessages(conversationId, currentUser.getUserId());

        // 获取关联的职位信息
        Job job = jobService.getJobById(conversation.getJobId());

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("messages", messages);
        model.addAttribute("conversationId", conversationId);
        model.addAttribute("currentUserId", currentUser.getUserId());
        model.addAttribute("job", job);
        model.addAttribute("conversation", conversation); // 添加对话对象

        return "nav/chat";
    }


    @GetMapping("/conversations")
    public String getConversations(
            @AuthenticationPrincipal CustomUserDetails currentUser,
            @RequestParam("jobId") Long jobId,
            Model model) {

        List<Conversation> conversations = messageService.getConversationsForJob(jobId);
        Job job = jobService.getJobById(jobId);

        model.addAttribute("conversations", conversations);
        model.addAttribute("job", job);
        return "nav/conversation-list";
    }

    @GetMapping("/user-conversations")
    public String getUserConversations(
            @AuthenticationPrincipal CustomUserDetails currentUser,
            Model model) {

        List<Conversation> conversations = messageService.getConversationsForUser(currentUser.getUserId());
        model.addAttribute("conversations", conversations);
        return "nav/user-conversation-list";
    }
}