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
 * 异常：
 *      1，校验基本类型参数(即直接校验参数)，需要在类上标注@Validated
 *          抛出：javax.validation.ConstraintViolationException
 *              get 基本参数类型    ：@GetMapping("/tst") public void tk(@Min(value = 1) Integer id)
 *      2，校验复杂封装对象(即校验参数的属性)
 *          抛出：org.springframework.validation.BindException
 *              get 复杂封装对象    ：@GetMapping("/tst") public void tk(@Valid Tk tk)
 *              post表单提交的方式  ：@PostMapping("/tst") public void tk(@Valid Tk tk)
 *      3，校验复杂封装对象(即校验参数的属性) ，通过json提交(即参数需要标注@RequestBody)       
 *          抛出：org.springframework.web.bind.MethodArgumentNotValidException
 *              post json提交的方式 ：@PostMapping("/tst") public void tk(@Valid @RequestBody Tk tk)
 *              
 * 注意：
 *      1，在非controller类上标注@Validated时，只有当参数标注有@Valid注解时才会进行校验
 *          void tk1(@Valid Tk tk);     有效
 *          void tk(@Validated  Tk tk); 无效
 *          void tk3( Tk tk);           无效
 *      2，在controller校验参数时，不加@Valid @Validated时也生效
 *          void tk1(@Valid Tk tk);     有效
 *          void tk(@Validated  Tk tk); 有效
 *          void tk3( Tk tk);           有效
 *          
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
