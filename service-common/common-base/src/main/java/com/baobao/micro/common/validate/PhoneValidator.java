package com.baobao.micro.common.validate;

import cn.hutool.core.lang.Validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author baobao
 * @create 2021-01-06 16:17
 * @description 手机号校验器
 */
public class PhoneValidator implements ConstraintValidator<Phone, String> {
    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        if (phone == null){
            return true;
        }
        return Validator.isMobile(phone);
    }
}
