package cn.wu.mock.mybatis.type;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MappedJdbcTypes {
    JdbcType[] value();
    boolean includeNullJdbcType() default false;
}
