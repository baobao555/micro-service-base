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
 * @create 2021-07-14 17:31
 * @description 身份证号校验注解
 */
@Target(FIELD)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = IdCardValidator.class)
public @interface IdCard {
    // 默认提示的消息
    String message() default "身份证号不合法";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
