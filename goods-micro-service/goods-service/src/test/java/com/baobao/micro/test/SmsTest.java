package com.baobao.micro.test;

import com.baobao.micro.common.sms.AliyunSmsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author baobao
 * @create 2022-04-06 11:22
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsTest {
    @Autowired
    private AliyunSmsService aliyunSmsService;

    @Test
    public void testAliyunSms() {
        aliyunSmsService.sendSms("", "", "", "");
    }
}
