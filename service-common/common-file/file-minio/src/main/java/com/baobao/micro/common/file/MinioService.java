package com.baobao.micro.common.file;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author baobao
 * @create 2022-03-23 16:06
 * @description
 */
@Slf4j
@Service
public class MinioService {
    @Autowired
    private MinioProperties minioProperties;

    @Autowired
    private MinioClient minioClient;

    /**
     * 上传文件到minio
     * @param in 文件输入流
     * @param dir 上传到bucket哪个目录，开头不要加/
     * @param originFileName 原始文件名
     * @param contentType 文件mine类型
     * @param fileSize 文件大小
     * @return 上传成功后文件在bucket中的相对路径，上传失败返回null
     */
    public String upload(InputStream in, String dir, String originFileName, String contentType, int fileSize) {
        // 随机化文件名
        String uuid = UUID.randomUUID().toString(true);
        String fileName = StrUtil.addPrefixIfNot(originFileName, uuid);
        String rootPath;
        if (StrUtil.isEmpty(dir)) {
            rootPath = "";
        }else {
            rootPath = dir.endsWith("/") ? dir : dir + "/";
        }
        // 将文件散列到日期目录
        String finalPath = rootPath + DateUtil.format(new Date(), "yyyyMMdd") + "/" + fileName;
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder().bucket(minioProperties.getBucket()).object(finalPath)
                    .contentType(contentType)
                    .stream(in, fileSize, -1)
                    .build();
            minioClient.putObject(putObjectArgs);
            return finalPath;
        } catch (Exception e) {
            log.error("文件上传失败", e);
            return null;
        }
    }

    /**
     * 获取文件固定访问url，需要设置bucket为公共权限
     * @param path 文件在bucket中的相对路径
     * @return 固定url
     */
    public String getFixAccessUrl(String path) {
        return StrUtil.isBlank(path) ? null : minioProperties.getEndpoint() + "/" + minioProperties.getBucket() + "/" + path;
    }

    /**
     * 获取文件临时访问url，bucket可设置为私有权限
     * @param path 文件在bucket中的相对路径
     * @return 临时访问url
     */
    public String getTempAccessUrl(String path) {
        if (StrUtil.isBlank(path)) {
            return null;
        }
        GetPresignedObjectUrlArgs presignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder()
                .bucket(minioProperties.getBucket())
                .object(path)
                .method(Method.GET)
                .expiry(minioProperties.getTempUrlExpire(), TimeUnit.SECONDS)
                .build();
        try {
            return minioClient.getPresignedObjectUrl(presignedObjectUrlArgs);
        } catch (Exception e) {
            log.error("获取文件临时访问url失败", e);
            return null;
        }
    }

    /**
     * 获取前端直传url
     * @param path 文件在bucket中的相对路径
     * @return 前端直传临时url
     */
    public String getDirectUploadUrl(String path) {
        if (StrUtil.isBlank(path)) {
            return null;
        }
        GetPresignedObjectUrlArgs presignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder()
                .bucket(minioProperties.getBucket())
                .object(path)
                .method(Method.PUT)
                .expiry(minioProperties.getDirectUploadUrlExpire(), TimeUnit.SECONDS)
                .build();
        try {
            return minioClient.getPresignedObjectUrl(presignedObjectUrlArgs);
        } catch (Exception e) {
            log.error("获取前端直传临时url失败", e);
            return null;
        }
    }
}
