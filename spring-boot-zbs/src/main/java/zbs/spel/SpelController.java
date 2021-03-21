package zbs.spel;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import zbs.config.mutipartDatasource.UserEntity;
import zbs.spel.aop.SpelAnnotation;

/**
 * @author zhangbaisen
 */
@RestController
public class SpelController {
    
    // http://localhost:8080/spel?msg=hello
    @GetMapping("/spel")
    @SpelAnnotation("#msg")
    public String spel(String msg){
        return msg;
    }
    // http://localhost:8080/spelBean?id=1&name=bobo&age=18
    @GetMapping("/spelBean")
    @SpelAnnotation("#user.name")
    public UserEntity spelBean(UserEntity user){
        return user;
    }

    // http://localhost:8080/spelNoArg
    @GetMapping("/spelNoArg")
//    @SpelAnnotation("#root")//返回null
//    @SpelAnnotation("'name'")//返回name
//    @SpelAnnotation("name")//不加单引号。会报错，SpelEvaluationException: EL1007E: Property or field 'name' cannot be found on null
    @SpelAnnotation()//会报错，所以需要在SpelAop中判断表达式不能为空字符串。IllegalStateException: No node
//    @SpelAnnotation("new String('路人甲java')")//返回路人甲java
    public String spelNoArg(){
        return "";
    }
    
    // http://localhost:8080/spelNull
    @GetMapping("/spelNull")
    @SpelAnnotation()
    public String spelNull(){
        return "";
    }

}
