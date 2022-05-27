package com.baobao.micro.file.aliyun;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author baobao
 * @create 2022-03-04 17:13
 * @description
 */
@ConfigurationProperties(prefix = "alibaba.cloud.oss")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AliyunOssProperties {
    /**endpoint*/
    private String endpoint;
    /**bucket名称*/
    private String bucket;
    /**前端直传回调地址*/
    private String policyCallback;
    /**前端直传policy有效期(秒)*/
    private Integer policyExpire;
    /**文件临时url有效期(秒)*/
    private Integer tempUrlExpire;
}
