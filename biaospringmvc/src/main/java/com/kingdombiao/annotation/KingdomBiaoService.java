package com.kingdombiao.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KingdomBiaoService {

    String value() default "";

}
