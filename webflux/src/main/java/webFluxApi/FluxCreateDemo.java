package webFluxApi;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 官网示例 https://tech.io/playgrounds/929/reactive-programming-with-reactor-3/Flux
 * @author zhangbaisen
 * @createTime 2021/4/30 16:47
 */
public class FluxCreateDemo {
    public static void main(String[] args) throws InterruptedException {
        List<Long> list = new ArrayList<Long>();
        list.add(1L);list.add(2L);list.add(3L);list.add(4L);
        
        Flux.fromIterable(list)
                .delayElements(Duration.ofMillis(1000))
//                .doOnNext(serviceA::someObserver)
                .doOnNext(i -> System.out.println("doOnNext:" + i))
                .map(d -> d * 2)
                .take(3)
//                .onErrorResumeWith(errorHandler::fallback)
                .doAfterTerminate(() -> System.out.println("terminate"))
                .subscribe(System.out::println);

        TimeUnit.SECONDS.sleep(10);
    }
    // TODO Return an empty Flux
    Flux<String> emptyFlux() {
        return Flux.empty();
    }

    // TODO Return a Flux that contains 2 values "foo" and "bar" without using an array or a collection
    Flux<String> fooBarFluxFromValues() {
        return Flux.just("foo","bar");
    }

    // TODO Create a Flux from a List that contains 2 values "foo" and "bar"
    Flux<String> fooBarFluxFromList() {
        List<String> list = new ArrayList<>();
        list.add("foo");
        list.add("bar");
        //String[] list = {"foo","bar"};
        return Flux.fromIterable(list);
    }

    // TODO Create a Flux that emits an IllegalStateException
    Flux<String> errorFlux() {
        return Flux.error(IllegalStateException::new);
    }

    // TODO Create a Flux that emits increasing values from 0 to 9 each 100ms
    Flux<Long> counter() {
        return Flux.interval(Duration.ZERO).take(10L);
    }



}
