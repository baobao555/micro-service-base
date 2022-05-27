package com.baobao.micro.common.validate;




import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author baobao
 * @create 2021-01-06 16:16
 * @description 手机号校验注解
 */
@Target({FIELD,PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = MobilePhoneValidator.class)
public @interface MobilePhone {
    // 默认提示的消息
    String message() default "手机号不合法";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
