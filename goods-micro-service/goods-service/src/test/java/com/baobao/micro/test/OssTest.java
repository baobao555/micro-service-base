package com.baobao.micro.test;

import com.baobao.micro.file.minio.MinioService;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * @author baobao
 * @create 2022-04-06 13:53
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@RequiredArgsConstructor
public class OssTest {
    /*@Autowired
    private AliyunOssService aliyunOssService;

    @Test
    public void testAliyunOss() {
        AliyunOssPolicy policy = aliyunOssService.getPolicy("aaa");
        String fixAccessUrl = aliyunOssService.getFixAccessUrl("guojia.jpg");
        String tempAccessUrl = aliyunOssService.getTempAccessUrl("guojia.jpg");
    }*/

    private final MinioService minioService;

    @Test
    public void testMinio() throws Exception {
        String directUploadUrl = minioService.getPutDirectUploadUrl("111.xlsx");
        String tempAccessUrl = minioService.getTempAccessUrl("111.xlsx");
        // Map<String, String> postDirectUploadPresignedInfo = minioService.getPostDirectUploadPresignedInfo("aaa/111.xlsx");
        Map<String, String> info = minioService.getUploadPresignedInfo("", "111.xlsx");
        System.out.println(tempAccessUrl);
    }
}
