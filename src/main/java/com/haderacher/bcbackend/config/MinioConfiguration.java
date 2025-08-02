package com.haderacher.bcbackend.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfiguration {

    @Value("${minio.endpoint}")
    private String endpoint;
    @Value("${minio.accessKey}")
    private String accessKey;
    @Value("${minio.secretKey}")
    private String secretKey;

    @Bean
    MinioClient minioClient() {

        return MinioClient.builder()
                .endpoint(this.endpoint)
                .credentials(this.accessKey, this.secretKey)
                .build();
    }
}
