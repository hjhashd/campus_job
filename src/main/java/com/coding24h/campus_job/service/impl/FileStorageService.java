package com.coding24h.campus_job.service.impl;

import com.coding24h.campus_job.exception.FileStorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;

@Service
public class FileStorageService {

    // 从配置文件中获取上传目录，默认为项目根目录下的 uploads
    @Value("${file.upload-dir:./uploads}")
    private String uploadDir;

    /**
     * 存储上传的文件并返回相对路径
     *
     * @param file 上传的文件
     * @param subdirectory 存储的子目录(如 licenses, tax_certs)
     * @return 文件的相对路径(如 licenses/f3d5e7a1-9c8b-4a2d-b102-6c0e8f3a1b5c.jpg)
     */
    public String storeFile(MultipartFile file, String subdirectory) {
        if (file == null || file.isEmpty()) {
            throw new FileStorageException("上传文件为空");
        }

        try {
            // 1. 验证文件类型
            String contentType = file.getContentType();
            if (contentType == null || !isValidFileType(contentType)) {
                throw new FileStorageException("不支持的文件类型: " + contentType);
            }

            // 2. 验证文件大小 (最大5MB)
            if (file.getSize() > 5 * 1024 * 1024) {
                throw new FileStorageException("文件大小超过5MB限制");
            }

            // 3. 创建项目根目录的存储目录
            Path basePath = Paths.get(uploadDir).toAbsolutePath().normalize();

            // 4. 创建子目录路径 (如 /project-root/uploads/licenses)
            Path storagePath = basePath.resolve(subdirectory);
            Files.createDirectories(storagePath);

            // 5. 生成唯一文件名 (保留原始扩展名)
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String uniqueName = UUID.randomUUID().toString() + extension;

            // 6. 存储文件到指定路径
            Path targetLocation = storagePath.resolve(uniqueName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // 7. 返回相对路径 (不包含绝对路径，只返回如 licenses/filename.jpg)
            return subdirectory + "/" + uniqueName;

        } catch (IOException ex) {
            throw new FileStorageException("文件存储失败: " + ex.getMessage(), ex);
        }
    }

    /**
     * 验证文件类型是否符合要求
     *
     * @param contentType 文件MIME类型
     * @return 是否有效
     */
    private boolean isValidFileType(String contentType) {
        return Arrays.asList("image/jpeg", "image/png", "image/gif", "application/pdf")
                .contains(contentType.toLowerCase());
    }

    /**
     * 获取存储的基础路径（用于日志或其他操作）
     */
    public String getBaseStoragePath() {
        return Paths.get(uploadDir).toAbsolutePath().toString();
    }

    public void deleteFile(String relativePath) {
        if (relativePath == null || relativePath.isEmpty()) return;

        try {
            Path filePath = Paths.get(uploadDir).resolve(relativePath);
            if (Files.exists(filePath)) {
                Files.delete(filePath);
                System.out.println("删除旧文件:" + relativePath);
            }
        } catch (IOException ex) {
            throw new FileStorageException("文件删除失败: " + relativePath, ex);
        }
    }
}