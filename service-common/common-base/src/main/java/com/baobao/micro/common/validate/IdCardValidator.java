package com.baobao.micro.common.validate;

import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.StrUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author baobao
 * @create 2021-07-14 17:32
 * @description 身份证号校验器
 */
public class IdCardValidator implements ConstraintValidator<IdCard, String>  {
    @Override
    public boolean isValid(String idCard, ConstraintValidatorContext constraintValidatorContext) {
        if (StrUtil.isBlank(idCard)){
            return true;
        }
        return IdcardUtil.isValidCard(idCard);
    }
}
