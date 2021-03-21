package zbs.provider.annotation;

import java.lang.annotation.*;

/**
 * 表
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CusTable {
    String value() default "";
}
