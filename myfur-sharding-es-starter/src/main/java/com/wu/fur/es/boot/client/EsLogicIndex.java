package com.wu.fur.es.boot.client;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EsLogicIndex {

    String value() default "";
}
