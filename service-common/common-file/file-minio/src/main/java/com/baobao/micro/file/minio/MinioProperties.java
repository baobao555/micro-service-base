package com.baobao.micro.file.minio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author baobao
 * @create 2022-03-23 15:01
 * @description Minio配置
 */
@ConfigurationProperties(prefix = "minio")
@Data
public class MinioProperties {
    /**endpoint*/
    private String endpoint;
    /**accessKey*/
    private String accessKey;
    /**secretKey*/
    private String secretKey;
    /**bucket*/
    private String bucket;
    /**文件临时访问url过期时间(秒)*/
    private Integer tempUrlExpire;
    /**前端直传url过期时间(秒)*/
    private Integer directUploadExpire;
}
