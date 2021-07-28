package zbs.controllerAnnotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangbaisen
 * @createTime 2021/4/27 17:11
 */
@RestController
@Slf4j
public class HelloController {
    
    @GetMapping("/himvc")
    public String hi1(){
        log.info("webmvc start ---");
        
        String res = sleep();
        log.info("webmvc end ---");
        return res;
    }
    
    @GetMapping("/hi")
    public Mono<String> hi(){
        log.info("webflux start ---");
        Mono<String> res = Mono.fromSupplier(this::sleep);
        log.info("webflux end ---");
        return res;
    }
    
    
    public String sleep(){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hi webflux";
    }
}
