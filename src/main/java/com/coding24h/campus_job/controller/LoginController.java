package com.coding24h.campus_job.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    // 添加根路径重定向
    @GetMapping("/")
    public String rootRedirect() {
        return "redirect:/index";
    }

    @GetMapping("/toLogin")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "用户名或密码错误");
        }
        return "login";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard() {

        return "dashboard";
    }

    @GetMapping("/ai-company")
    public String showAICompanyPage() {
        return "nav/ai_company"; // 如果使用Thymeleaf，返回模板名称
    }

}

