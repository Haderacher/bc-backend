package com.haderacher.bcbackend.controller;

import com.haderacher.bcbackend.service.ResumeService;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/resumes")
@Slf4j
public class ResumeController {

    @Autowired
    MinioClient minioClient;

    @Autowired
    ResumeService resumeService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadResume(
            @RequestParam("resume") MultipartFile file,
            @RequestParam("userId") String userId) {

        // 1. 基本的文件校验
        if (file.isEmpty()) {
            log.warn("Upload attempt with an empty file for user ID: {}", userId);
            return new ResponseEntity<>(
                    Collections.singletonMap("error", "上传失败：文件不能为空。"),
                    HttpStatus.BAD_REQUEST
            );
        }

        // 2. (可选) 文件类型校验
        List<String> allowedTypes = List.of("application/pdf", "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        if (!allowedTypes.contains(file.getContentType())) {
            log.warn("Upload attempt with unsupported file type '{}' for user ID: {}", file.getContentType(), userId);
            return new ResponseEntity<>(
                    Collections.singletonMap("error", "上传失败：仅支持PDF, DOC, DOCX格式。"),
                    HttpStatus.UNSUPPORTED_MEDIA_TYPE
            );
        }

        try {
            // 3. 调用Service层处理业务逻辑
            log.info("Received resume upload request for user ID: {}", userId);
            String fileKey = resumeService.processAndStoreResume(file, userId);

            // 4. 返回成功的响应
            log.info("Successfully processed resume for user ID: {}. File key: {}", userId, fileKey);
            Map<String, String> response = Map.of(
                    "message", "简历上传成功！",
                    "fileKey", fileKey,
                    "userId", String.valueOf(userId)
            );
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // 5. 捕获并处理未知异常
            log.error("Error uploading resume for user ID: " + userId, e);
            return new ResponseEntity<>(
                    Collections.singletonMap("error", "服务器内部错误，上传失败：" + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
