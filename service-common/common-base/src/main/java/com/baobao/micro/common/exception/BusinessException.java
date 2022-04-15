package com.baobao.micro.common.exception;

/**
 * @author baobao
 * @create 2021-09-09 14:19
 * @description 项目中跟具体业务相关的自定义异常
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
