package com.baobao.micro.common.file;

import cn.hutool.core.util.StrUtil;
import com.alibaba.cloud.spring.boot.context.condition.ConditionalOnAliCloudEndpoint;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author baobao
 * @create 2022-03-04 16:10
 * @description 阿里云oss服务
 */
@Service
@ConditionalOnAliCloudEndpoint
@EnableConfigurationProperties(AliyunOssProperties.class)
@Slf4j
public class AliyunOssService {
    @Autowired
    private OSSClient ossClient;

    @Autowired
    private AliyunOssProperties aliyunOssProperties;

    /**
     * 前端获取直传所需policy
     * @param dir 指定上传到bucket中的哪个目录
     * @return policy
     */
    public AliyunOssPolicy getPolicy(String dir) {
        // 将文件分散到日期目录下
        String date = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now());
        // 最终上传到bucket的路径为dir/日期/文件
        String finalDir;
        if (StrUtil.isBlank(dir)) {
            finalDir = date + "/";
        }else {
            finalDir = dir.endsWith("/") ? dir + date + "/" : dir + "/" + date + "/";
        }
        // host的格式为 bucketname.endpoint
        String bucketName = aliyunOssProperties.getBucket();
        String endpoint = aliyunOssProperties.getEndpoint();
        Integer policyExpire = aliyunOssProperties.getPolicyExpire();
        String host = "https://" + bucketName + "." + endpoint;
        try {
            long expireEndTime = System.currentTimeMillis() + policyExpire * 1000;
            Date expiration = new Date(expireEndTime);
            // PostObject请求最大可支持的文件大小为5 GB，即CONTENT_LENGTH_RANGE为5*1024*1024*1024。
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, finalDir);
            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);

            return AliyunOssPolicy.builder()
                    .accessid(ossClient.getCredentialsProvider().getCredentials().getAccessKeyId())
                    .policy(encodedPolicy)
                    .signature(postSignature)
                    .dir(finalDir)
                    .host(host)
                    .expire(String.valueOf(expireEndTime / 1000)).build();
        } catch (Exception e) {
            log.error("获取policy失败", e);
            return null;
        }
    }
}
