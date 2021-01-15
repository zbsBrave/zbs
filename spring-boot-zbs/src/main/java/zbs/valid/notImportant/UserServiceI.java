package zbs.valid.notImportant;

import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author zhangbaisen
 * @since 2021/1/15
 */
//@Validated
public interface UserServiceI {

    void say(@Valid BeanUser user);
    void say1(@NotNull String name);
    void myValid(BeanUser user);
}
