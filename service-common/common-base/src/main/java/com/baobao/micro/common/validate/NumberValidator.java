package com.baobao.micro.common.validate;

import cn.hutool.core.lang.Validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author baobao
 * @create 2021-01-06 16:17
 * @description 数字校验器
 */
public class NumberValidator implements ConstraintValidator<Number, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null){
            return true;
        }
        return Validator.isNumber(value);
    }
}