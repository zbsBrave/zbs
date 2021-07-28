package zbs.controllerAnnotation;

import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import zbs.mongo.bean.User;
import zbs.mongo.dao.UserRepository;

import javax.annotation.Resource;

/**
 * @author zhangbaisen
 * @createTime 2021/4/30 15:25
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Resource
    private ReactiveMongoTemplate template;
    
    /** application/stream+json */
//    @GetMapping(value = "", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    @GetMapping(value = "", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> ssl(){
        return userRepository.findAll().log();
    }
    
    @PostMapping("")
    public Mono<User> save(@RequestBody User user){
        System.out.println(user);
        return userRepository.save(user);
    }
    
    @DeleteMapping("/{name}")
    public Mono<DeleteResult> del(@PathVariable("name") String name){
        return template.remove(new Query(Criteria.where("name").is(name)),"user");
    }
    
    @GetMapping("/{name}")
    public Mono<User> get(@PathVariable String name){
        return userRepository.findByName(name);
    }
    
    @GetMapping("")
    public Flux<User> getAll(){
        return userRepository.findAll();
    }
}
