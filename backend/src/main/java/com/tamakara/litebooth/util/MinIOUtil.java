package com.tamakara.litebooth.util;

import com.tamakara.litebooth.config.MinioConfig;
import io.minio.*;
import io.minio.http.Method;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Component
@RequiredArgsConstructor
public class MinIOUtil {
    private final MinioConfig minioConfig;
    private MinioClient minioClient;

    @PostConstruct
    public void init() {
        try {
            // 初始化 MinIO 客户端
            minioClient = MinioClient.builder()
                    .endpoint(minioConfig.getEndpoint())
                    .credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey())
                    .build();

            // 检查并创建桶
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioConfig.getBucketName()).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioConfig.getBucketName()).build());
            }
        } catch (Exception e) {
            throw new RuntimeException("MinIO初始化失败", e);
        }
    }

    public void upload(String hash, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("上传文件不能为空");
        }

        try (InputStream inputStream = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioConfig.getBucketName())
                            .object(hash)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());
        } catch (Exception e) {
            throw new RuntimeException("上传文件到MinIO失败", e);
        }
    }

    public String generateFileUrl(String hash, int expirationTime) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket(minioConfig.getBucketName())
                            .object(hash)
                            .method(Method.GET)
                            .expiry(expirationTime)
                            .build());
        } catch (Exception e) {
            throw new RuntimeException("生成文件URL失败", e);
        }
    }
}