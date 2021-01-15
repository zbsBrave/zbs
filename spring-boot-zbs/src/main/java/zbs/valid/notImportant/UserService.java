package zbs.valid.notImportant;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * @author zhangbaisen
 * @since 2021/1/15
 */
@Service
@Validated
public class UserService implements UserServiceI{
    @Override
    public void say( BeanUser user) {
        System.out.println("userService say:"+user.toString());
    }

    @Override
    public void say1( String name) {
        System.out.println("userService say1, name: "+name);
    }

    @Override
    public void myValid(BeanUser user) {
        System.out.println("自定义valid userService myValid:"+user.toString());
    }
}
