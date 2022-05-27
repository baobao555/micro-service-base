package com.baobao.micro.file.minio;

import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author baobao
 * @create 2022-03-23 15:03
 * @description Minio配置类
 */
@EnableConfigurationProperties(MinioProperties.class)
@Configuration
@ComponentScan("com.baobao.micro.file.minio")
@RequiredArgsConstructor
public class MinioAutoConfiguration {
    private final MinioProperties minioProperties;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder().endpoint(minioProperties.getEndpoint())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
    }
}
