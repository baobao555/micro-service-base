package com.baobao.micro.file.aliyun;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.alibaba.cloud.spring.boot.context.condition.ConditionalOnAliCloudEndpoint;
import com.alibaba.cloud.spring.boot.context.env.AliCloudProperties;
import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.nio.charset.StandardCharsets;
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
@RequiredArgsConstructor
public class AliyunOssService {
    private final OSS ossClient;

    private final AliyunOssProperties aliyunOssProperties;

    private final AliCloudProperties aliCloudProperties;

    /**
     * 前端获取直传所需policy
     * @param dir 指定上传到bucket中的哪个目录，开头不需要加/
     * @return policy
     */
    public AliyunOssPolicy getPolicy(String dir) {
        // 将文件分散到日期目录下
        String date = DateUtil.thisYear() + "/" + (DateUtil.thisMonth() + 1) + "/" + DateUtil.thisDayOfMonth();
        // 最终上传到bucket的路径为dir/年/月/日/文件
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
                .accessid(aliCloudProperties.getAccessKey())
                .policy(encodedPolicy)
                .signature(postSignature)
                .dir(finalDir)
                .host(host)
                .expire(String.valueOf(expireEndTime / 1000)).build();
    }

    /**
     * 获取文件固定访问url(需要对bucket设置公共读)
     * @param path 文件在bucket中的路径
     * @return 固定url
     */
    public String getFixAccessUrl(String path) {
        Assert.isTrue(StrUtil.isNotBlank(path), "路径不能为空");
        String bucketName = aliyunOssProperties.getBucket();
        String endpoint = aliyunOssProperties.getEndpoint();
        return "https://" + bucketName + "." + endpoint + "/" + path;
    }

    /**
     * 获取文件临时访问url(bucket设置为私有时)
     * @param path 文件在bucket中的路径
     * @return 临时url
     */
    public String getTempAccessUrl(String path) {
        Assert.isTrue(StrUtil.isNotBlank(path), "路径不能为空");
        String bucketName = aliyunOssProperties.getBucket();
        Integer tempUrlExpire = aliyunOssProperties.getTempUrlExpire();
        // 设置URL过期时间
        Date expiration = new Date(System.currentTimeMillis() + tempUrlExpire * 1000);
        // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。其中objectName即文件在Bucket中的路径
        URL url = ossClient.generatePresignedUrl(bucketName, path, expiration);
        return url.toString();
    }
}
