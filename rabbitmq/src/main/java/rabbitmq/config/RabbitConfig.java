package rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 声明交换机和队列
 *      durable:是否持久化,默认是true,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
 *      exclusive:默认是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
 *      autoDelete:是否自动删除，默认false。当没有生产者或者消费者使用此队列，该队列会自动删除。
 * @author zhangbaisen
 */
@Configuration
public class RabbitConfig {
    public static final String delay_exchange = "delay.exchange";
    public static final String delay_queue_a = "delay.queue.a";
    public static final String delay_routing_key_a = "delay.routing.key.a";

    public static final String dead_letter_exchange = "dead.letter.exchange";//死信交换机
    public static final String dead_letter_queue = "dead.letter.queue";//死信队列
    public static final String dead_routing_key = "dead.routing.key";//死信路由键

    @Bean("delayExchange")
    public DirectExchange delayExchange(){
        return new DirectExchange(delay_exchange,false,false);
    }
    /**  声明延迟队列a */
    @Bean("delayQueueA")
    public Queue delayQueueA(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", dead_letter_exchange);//声明当前队列绑定的死信交换机
        args.put("x-dead-letter-routing-key", dead_routing_key);//声明当前队列的死信路由key
        args.put("x-message-ttl", 5000);//声明队列的TTL，单位毫秒
        return new Queue(delay_queue_a,false,false,false,args);
    }
    @Bean
    public Binding delayBingA(@Qualifier("delayExchange") DirectExchange delayExchange,
                              @Qualifier("delayQueueA") Queue delayQueueA){
        return BindingBuilder.bind(delayQueueA).to(delayExchange).with(delay_routing_key_a);
    }

    /**  声明死信交换机 */
    @Bean("deadLetterExchange")
    public DirectExchange deadLetterExchange(){
        return new DirectExchange(dead_letter_exchange,false,false);
    }
    /**  声明死信队列 */
    @Bean("deadLetterQueue")
    public Queue deadLetterQueue(){
        return new Queue(dead_letter_queue,false,false,false);
    }
    /**  绑定死信交换机和死信队列 */
    @Bean
    public Binding deadRoutingKey(@Qualifier("deadLetterExchange") DirectExchange deadLetterExchange,
                                  @Qualifier("deadLetterQueue") Queue deadLetterQueue){
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with(dead_routing_key);
    }
}
