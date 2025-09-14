package com.coding24h.campus_job.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.beans.factory.annotation.Value;
import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir:./uploads}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 创建绝对路径
        String absolutePath = new File(uploadDir).getAbsolutePath();

        // 添加后斜杠确保路径正确
        if (!absolutePath.endsWith(File.separator)) {
            absolutePath += File.separator;
        }

        // 映射 /uploads/** 到文件系统的 uploads 目录
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + absolutePath)
                .setCachePeriod(86400); // 24小时缓存
    }
}