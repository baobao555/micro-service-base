package com.baobao.micro.file.minio;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PostPolicy;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author baobao
 * @create 2022-03-23 16:06
 * @description
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MinioService {
    private final MinioProperties minioProperties;

    private final MinioClient minioClient;

    /**
     * 上传文件到minio
     * @param in 文件输入流
     * @param dir 上传到bucket哪个目录，开头不要加/
     * @param originFileName 原始文件名
     * @param contentType 文件mine类型
     * @param fileSize 文件大小
     * @return 上传成功后文件在bucket中的相对路径，上传失败返回null
     */
    public String upload(InputStream in, String dir, String originFileName, String contentType, int fileSize) throws Exception {
        String finalPath = generateFilePath(dir, originFileName);
        PutObjectArgs putObjectArgs = PutObjectArgs.builder().bucket(minioProperties.getBucket()).object(finalPath)
                .contentType(contentType)
                .stream(in, fileSize, -1)
                .build();
        minioClient.putObject(putObjectArgs);
        return finalPath;
    }

    /**
     * 根据原始文件名生成随机化文件名，并散列到日期目录中
     * @param dir 上传到bucket哪个目录，开头不要加/
     * @param originFileName 原始文件名
     * @return 最终生成的文件在bucket中的路径
     */
    private String generateFilePath(String dir, String originFileName) {
        // 随机化文件名
        String uuid = UUID.randomUUID().toString(true);
        String fileName = StrUtil.addPrefixIfNot(originFileName, uuid);
        String rootPath;
        if (StrUtil.isBlank(dir)) {
            rootPath = "";
        }else {
            rootPath = dir.endsWith("/") ? dir : dir + "/";
        }
        // 将文件散列到年/月/日目录
        return rootPath + DateUtil.thisYear() + "/" + (DateUtil.thisMonth() + 1) + "/" + DateUtil.thisDayOfMonth() + "/" + fileName;
    }

    /**
     * 获取文件固定访问url，需要设置bucket为公共权限
     * @param path 文件在bucket中的相对路径
     * @return 固定url
     */
    public String getFixAccessUrl(String path) {
        Assert.isTrue(StrUtil.isNotBlank(path), "路径不能为空");
        return minioProperties.getEndpoint() + "/" + minioProperties.getBucket() + "/" + path;
    }

    /**
     * 获取文件临时访问url，bucket可设置为私有权限
     * @param path 文件在bucket中的相对路径
     * @return 临时访问url
     */
    public String getTempAccessUrl(String path) throws Exception {
        Assert.notBlank(path, "路径不能为空");
        GetPresignedObjectUrlArgs presignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder()
                .bucket(minioProperties.getBucket())
                .object(path)
                .method(Method.GET)
                .expiry(minioProperties.getTempUrlExpire(), TimeUnit.SECONDS)
                .build();
        return minioClient.getPresignedObjectUrl(presignedObjectUrlArgs);
    }

    /**
     * 获取PUT方式前端直传url
     * @param path 文件在bucket中的相对路径
     * @return PUT方式前端直传临时url
     */
    public String getPutDirectUploadUrl(String path) throws Exception {
        Assert.isTrue(StrUtil.isNotBlank(path), "路径不能为空");
        GetPresignedObjectUrlArgs presignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder()
                .bucket(minioProperties.getBucket())
                .object(path)
                .method(Method.PUT)
                .expiry(minioProperties.getDirectUploadExpire(), TimeUnit.SECONDS)
                .build();
        return minioClient.getPresignedObjectUrl(presignedObjectUrlArgs);
    }

    /**
     * 获取POST方式前端直传预签名信息
     * @param path 文件在bucket中的相对路径
     * @return 前端直传预签名信息
     */
    public Map<String, String> getPostDirectUploadPresignedInfo(String path) throws Exception {
        Assert.isTrue(StrUtil.isNotBlank(path), "路径不能为空");
        // 创建一个上传策略
        PostPolicy policy = new PostPolicy(minioProperties.getBucket(), ZonedDateTime.now().plusSeconds(minioProperties.getDirectUploadExpire()));
        // 设置一个参数key，值为上传对象的名称
        policy.addEqualsCondition("key", path);
        // 添加Content-Type以"image/"开头，表示只能上传照片
        // policy.addStartsWithCondition("Content-Type", "image/");
        // 设置上传文件的大小 64kiB to 10MiB.
        // policy.addContentLengthRangeCondition(64 * 1024, 10 * 1024 * 1024);
        Map<String, String> map = minioClient.getPresignedPostFormData(policy);
        // 将文件在bucket中的最终路径回传给前端
        map.put("key", path);
        // 将上传url传递给前端
        map.put("url", minioProperties.getEndpoint() + "/" + minioProperties.getBucket());
        return map;
    }

    /**
     * 获取post前端直传预签名信息
     * @param dir 上传到bucket哪个目录，开头不要加/
     * @param originFileName 文件原始名称
     * @return 预签名信息
     */
    public Map<String, String> getUploadPresignedInfo(String dir, String originFileName) throws Exception {
        String filePath = this.generateFilePath(dir, originFileName);
        return this.getPostDirectUploadPresignedInfo(filePath);
    }
}
