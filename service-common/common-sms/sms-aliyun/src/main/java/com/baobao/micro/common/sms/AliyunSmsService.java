package com.baobao.micro.common.sms;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.alibaba.cloud.spring.boot.context.condition.ConditionalOnAliCloudEndpoint;
import com.alibaba.cloud.spring.boot.sms.ISmsService;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author baobao
 * @create 2022-03-03 17:00
 * @description 阿里云短信服务
 */
@Service
@Slf4j
@ConditionalOnAliCloudEndpoint
public class AliyunSmsService {
    @Autowired
    private ISmsService smsService;

    /**
     * 发送单条短信
     * @param signName 短信签名
     * @param templateCode 模板码
     * @param phoneNumber 手机号
     * @param param 短信参数json串
     */
    public void sendSms(String signName, String templateCode, String phoneNumber, String param) {
        // 校验手机号
        if (!Validator.isMobile(phoneNumber)) {
            log.error(StrUtil.format("手机号{}不合法", phoneNumber));
            return;
        }
        // 组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        // 必填:待发送手机号
        request.setPhoneNumbers(phoneNumber);
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        // 可选:模板中的变量替换JSON串
        request.setTemplateParam(param);
        try {
            SendSmsResponse smsResponse = smsService.sendSmsRequest(request);
        } catch (Exception e) {
            log.error(StrUtil.format("{}短信发送失败", phoneNumber), e);
        }
    }

    /**
     * 批量发送短信
     * @param signName 短信签名
     * @param templateCode 模板码
     * @param phoneNumbers 手机号集合
     * @param param 短信参数json串
     */
    public void sendSms(String signName, String templateCode, List<String> phoneNumbers, String param) {
        // 校验手机号，挑出正确的手机号
        if (CollUtil.isEmpty(phoneNumbers)) {
            log.error("手机号为空");
            return;
        }
        String rightPhoneNumbers = phoneNumbers.stream().filter(Validator::isMobile).collect(Collectors.joining(","));
        // 组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        // 必填:待发送手机号
        request.setPhoneNumbers(rightPhoneNumbers);
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        // 可选:模板中的变量替换JSON串
        request.setTemplateParam(param);
        try {
            SendSmsResponse sendSmsResponse = smsService.sendSmsRequest(request);
        } catch (Exception e) {
            log.error(StrUtil.format("{}短信发送失败", rightPhoneNumbers), e);
        }
    }
}
