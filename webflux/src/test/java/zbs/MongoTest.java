package zbs;

import jdk.nashorn.internal.objects.annotations.Where;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Mono;
import zbs.mongo.bean.User;

import javax.annotation.Resource;

import java.util.concurrent.CountDownLatch;

import static com.jayway.jsonpath.Criteria.where;

/**
 * @author zhangbaisen
 * @createTime 2021/4/30 14:35
 */
@SpringBootTest
@Slf4j
public class MongoTest {
    @Resource
    private ReactiveMongoTemplate template;
    
    @Test
    public void test() throws InterruptedException {
//        CountDownLatch latch = new CountDownLatch(1);
//        
//        template.insert(getUser("bobo"))
//                .flatMap(u -> template.findOne(new Query(Criteria.where("name").is("Joe")), User.class))
//                .doOnNext(System.out::println)
//                .flatMap(user -> template.dropCollection(User.class))
//                .doOnCancel(latch::countDown).subscribe();
//        latch.await();
                
                        
    }
    
    
    public static User getUser(String name){
        User user = new User();
        user.setName(name);
        user.setSex(0);
        user.setPhone("18976543213");
        return user;
    }
}
