package com.kingdombiao.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KingdomBiaoController {

    String value() default "";

}
