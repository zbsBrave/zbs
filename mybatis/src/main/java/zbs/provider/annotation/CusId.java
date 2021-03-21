package zbs.provider.annotation;

import java.lang.annotation.*;

/**
 * 主键
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CusId {
    String value() default "";
}
