package webFluxApi;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;

/**
 * just()：可以指定序列中包含的全部元素。创建出来的 Flux 序列在发布这些元素之后会自动结束。
 * fromArray()，fromIterable()和 fromStream()：可以从一个数组、Iterable 对象或 Stream 对象中创建 Flux 对象。
 * empty()：创建一个不包含任何元素，只发布结束消息的序列。
 * error(Throwable error)：创建一个只包含错误消息的序列。
 * never()：创建一个不包含任何消息通知的序列。
 * range(int start, int count)：创建包含从 start 起始的 count 个数量的 Integer 对象的序列。
 * interval(Duration period)和 interval(Duration delay, Duration period)：创建一个包含了从 0 开始递增的 Long 对象的序列。其中包含的元素按照指定的间隔来发布。除了间隔时间之外，还可以指定起始元素发布之前的延迟时间。
 * @author zhangbaisen
 * @createTime 2021/4/16 17:39
 */
public class FluxTest {
    public static void main(String[] args) throws InterruptedException {
        Flux.just("Hello", "World").subscribe(System.out::println);
        Flux.fromArray(new Integer[] {1, 2, 3}).subscribe(System.out::println);
        Flux.range(1, 3).subscribe(System.out::println);// 从1开始的3个int
        //Flux.interval(Duration.of(10, ChronoUnit.SECONDS)).subscribe(System.out::println);

        // generate: 同步地、逐个地产生值，next() 方法在每次回调的时候最多只能被调用一次
        // 也可以调用 error(Throwable) 或者 complete()
        // 第三个参数stateConsumer能够记录一个状态值（state），可以用于关闭连接
        Random random = new Random();
        Flux.generate(ArrayList::new,
                        (list, sink) -> {
                            int val = random.nextInt(100);
                            list.add(val);
                            sink.next(val);
                            if (list.size() == 3) {
                                sink.complete();
                            }
                            return list;
                        }, 
                        (state) -> System.out.println("stag:"+state))   //这里的state其实就是前面的list
                .subscribe(System.out::println);

        System.out.println("-----------------------------------------------------------------------");
        // create()方法与 generate()方法的不同之处在于所使用的是 FluxSink 对象。FluxSink 支持同步和异步的消息产生，并且可以在一次调用中产生多个元素
        System.out.println("id:"+Thread.currentThread().getId());
        Flux.create(sink -> {   //在一次调用中就产生了全部的 10 个元素
            System.out.println("id:"+Thread.currentThread().getId());
            for (int i = 0; i < 2; i++) {
                sink.next(i);
            }
            sink.complete();
        }).subscribe(System.out::println);
    }
}
