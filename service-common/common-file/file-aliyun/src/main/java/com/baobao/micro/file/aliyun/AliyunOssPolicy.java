package com.baobao.micro.file.aliyun;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author baobao
 * @create 2022-03-04 16:31
 * @description 前端直传policy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AliyunOssPolicy {
    private String accessid;
    private String policy;
    private String signature;
    private String dir;
    private String host;
    private String expire;
}
