package com.baobao.micro.common.validate;




import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author baobao
 * @create 2021-01-06 16:16
 * @description 数字校验注解：校验字符串是否是一个数字
 */
@Target(FIELD)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = NumberValidator.class)
public @interface Number {
    // 默认提示的消息
    String message() default "不是一个数字";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
