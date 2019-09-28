package com.kingdombiao.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KingdomBiaoQualifier {

    String value() default "";

}
