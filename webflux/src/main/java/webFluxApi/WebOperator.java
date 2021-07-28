package webFluxApi;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * operator 操作符
 *      map - 元素映射为新元素
 *      flatMap - 元素映射为流 需要返回一个Publisher
 *      filter
 *      zip
 * @author zhangbaisen
 * @createTime 2021/4/12 14:11
 */
public class WebOperator {
    public static void main(String[] args) {
        Mono.just(1).map(a->a*10).subscribe(System.out::println);
        Flux.just(1,2,3,4,5,6).map( a -> a+"| ").subscribe(System.out::print);
        System.out.println();
        System.out.println();

        //flatMap()
        Flux.just("flux","mono")
                .flatMap(s -> Flux.fromArray(s.split("")))
                .subscribe(System.out::println);
        
        Flux.zip(Flux.just("A","B","C"),Flux.just(1,2,3)).subscribe(System.out::println);
    }
}
