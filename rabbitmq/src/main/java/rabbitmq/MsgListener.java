package rabbitmq;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import rabbitmq.config.RabbitConfig;

import java.io.IOException;
import java.util.Date;

/**
 * 接收消息
 * @author zhangbaisen
 */
@Component
@Slf4j
public class MsgListener {
    /**  监听死信队列 */
    @RabbitListener(queues = RabbitConfig.dead_letter_queue)
    public void receiveA(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("当前时间：{},死信队列A收到消息：{}",new Date(),msg);
        //channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
