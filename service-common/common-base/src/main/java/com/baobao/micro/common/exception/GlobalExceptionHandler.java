package com.baobao.micro.common.exception;


import com.baobao.micro.common.domain.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * @author baobao
 * @create 2021-09-09 22:31
 * @description 全局异常处理器
 */
@ConditionalOnWebApplication
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 自定义业务异常
     */
    @ExceptionHandler(CustomException.class)
    public Result<Void> handleCustomException(CustomException e) {
        return Result.error(e.getMessage());
    }

    /**
     * 数据重复异常
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public Result<Void> handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return Result.error("数据重复");
    }

    /**
     * 数据校验异常
     */
    @ExceptionHandler(BindException.class)
    public Result<Void> handleValidateException(BindException e) {
        log.error(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return Result.error(message);
    }

    /**
     * 数据校验异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<Void> handleValidateException(ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        String message = e.getConstraintViolations().iterator().next().getMessage();
        return Result.error(message);
    }

    /**
     * 数据校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleValidateException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return Result.error(message);
    }

    /**
     * 参数非法异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<Void> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error(e.getMessage(), e);
        return Result.error(e.getMessage());
    }

    /**
     * 其余未知异常
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleOtherException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.error("系统繁忙");
    }
}
