package com.baobao.micro.common.validate;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author baobao
 * @create 2021-01-06 16:17
 * @description 手机号校验器
 */
public class MobilePhoneValidator implements ConstraintValidator<MobilePhone, String> {
    @Override
    public boolean isValid(String mobilePhone, ConstraintValidatorContext constraintValidatorContext) {
        if (StrUtil.isBlank(mobilePhone)){
            return true;
        }
        return Validator.isMobile(mobilePhone);
    }
}
