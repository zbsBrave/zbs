package zbs.mongo.dao;


import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import zbs.mongo.bean.User;

/**
 * ReactiveCrudRepository的泛型分别是User和ID的类型
 * @author zhangbaisen
 * @createTime 2021/4/30 14:29
 */
@Repository
public interface UserRepository extends ReactiveCrudRepository<User,String> {
    Mono<User> findByName(String name);
    
}
