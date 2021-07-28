package webFluxApi;

import reactor.core.publisher.Mono;

import java.time.Duration;


/**
 * 官网示例 https://tech.io/playgrounds/929/reactive-programming-with-reactor-3/Mono
 * @author zhangbaisen
 * @createTime 2021/4/30 17:47
 */
public class MonoCreateDemo {
    public static void main(String[] args) {
        Mono.just(1)
                .map(integer -> "foo" + integer)
//                .or(Mono.delay(Duration.ofMillis(100)))
                .subscribe(System.out::println);
    }


}
