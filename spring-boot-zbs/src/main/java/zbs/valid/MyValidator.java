package zbs.valid;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * 自定义校验规则
 * 参考 org.springframework.validation.Validator
 * @author zhangbaisen
 * @since 2021/1/15
 */
public class MyValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
