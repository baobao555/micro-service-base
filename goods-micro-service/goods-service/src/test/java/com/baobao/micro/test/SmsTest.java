package com.baobao.micro.test;

import com.baobao.micro.sms.aliyun.AliyunSmsService;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author baobao
 * @create 2022-04-06 11:22
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@RequiredArgsConstructor
public class SmsTest {
    private final AliyunSmsService aliyunSmsService;

    @Test
    public void testAliyunSms() {
        try {
            aliyunSmsService.sendSms("", "", "", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
