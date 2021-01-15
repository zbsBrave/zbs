package zbs.valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import zbs.valid.notImportant.BeanUser;
import zbs.valid.notImportant.UserServiceI;

import javax.annotation.Resource;

/**
 * @author zhangbaisen
 * @since 2021/1/15
 */
@RestController
public class ValidTestController {

    @Resource(name = "userService")
    private UserServiceI userService;

    // 默认接收所有content-type的请求
    // application/x-www-form-urlencoded , multipart/form-data 这两种content-type不能通过@requestBody解析
    @PostMapping(value = "/pst")
    public BeanUser pst(@Validated BeanUser user){
        System.out.println("无限制"+user);
        return user;
    }
    // 只接受 content-type = application/json 的请求
    @PostMapping(value = "/pst",consumes = "application/json")
    public BeanUser pst1(@RequestBody @Validated BeanUser user){
        System.out.println("限制 content-type = application/json"+user);
        return user;
    }

    @GetMapping("/my")
    public BeanUser tst(){
        BeanUser user = new BeanUser();

        //自己校验。参考 BeanValidationPostProcessor
        ValidUtil.valid(user);

        userService.myValid(user);
        return user;
    }

    public static void main(String[] args) {
        BeanUser user = new BeanUser();
        user.setId(1);
        ValidUtil.valid(user);
    }

    //参考 MethodValidationPostProcessor
    //1，在controller可以无配置使用valid，但是如果想在其他地方使用，必须在接口或其实现类上添加@Validated注解
    //  加在接口上，则它的所有实现类都能使用valid功能
    //  加在实现类上，只有该实现类能使用valid功能，其他未加valid的实现类不能使用valid功能
    //比如：@Validated public interface UserServiceI{}
    //
    //2，如果要校验bean，必须使用@Valid，使用@Validated无效
    //void say(@Valid BeanUser user); 这里使用@Validated无效，但是使用@Valid又没有group功能
    //void say1(@NotNull String name);
    @GetMapping("/say")
    public BeanUser say(){
        BeanUser user = new BeanUser();
        userService.say(user);
        return null;
    }
    @GetMapping("/say1")
    public BeanUser say1(){
        userService.say1(null);
        return null;
    }
}
