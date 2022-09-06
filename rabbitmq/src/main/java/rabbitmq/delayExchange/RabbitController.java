package rabbitmq.delayExchange;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rabbitmq.delayExchange.RabbitConfig;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@Slf4j
@ConditionalOnProperty(name = "my.delay.exchange", havingValue = "true", matchIfMissing = false)
public class RabbitController {
    @Resource
    private RabbitTemplate rabbitTemplate;
    /**  发送消息给延时队列 */
    @RequestMapping("/delay")
    public void sendMsgToDelayQueueA(String msg){
        log.info("当前时间：{},发送消息：{}",new Date(),msg);
        rabbitTemplate.convertAndSend(RabbitConfig.delay_exchange,RabbitConfig.delay_routing_key_a,msg);
//        rabbitTemplate.convertAndSend(RabbitConfig.delay_exchange,RabbitConfig.delay_routing_key_a,msg,
//                new CorrelationData("123"));
    }
}
