package netty;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangbaisen
 * @createTime 2021/3/31 17:04
 */
@RestController
public class AnnotationController {
    @GetMapping("/hi")
    public String hi(){
        return "this annotation type,threadId:"+Thread.currentThread().getId();
    }
}
