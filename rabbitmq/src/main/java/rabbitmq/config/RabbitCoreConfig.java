package rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangbaisen
 */
@Configuration
@Slf4j
public class RabbitCoreConfig {
    /**
     * 
     * 参考org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration
     */
    @Bean
    public RabbitTemplate rabbitTemplate(RabbitTemplateConfigurer configurer, ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate();
        
        //confirm确认机制，即消息到达交换机
        template.setConfirmCallback( (correlationData, ack, cause) -> {
            if(!ack){
                log.error("publishConfirm消息发送到交换器被退回，MessageId：{}",correlationData.getId());
            }else {
                // 如果使用 rabbitTemplate.convertAndSend(exchange,routing_key_a,msg) 发送消息，
                // 会出现correlationData=null的情况
                // 这是由于 rabbitTemplate.convertAndSend()默认传递一个null的correlationData，可以参考该部分源码。
                // 解决方法就是 rabbitTemplate.convertAndSend(exchange,routing_key_a,msg, new CorrelationData(id) )
                // log.info("发送消息到交换器成功,MessageId:{}",correlationData.getId());
                log.info("发送消息到交换器成功");
            }
        });
        
        
        configurer.configure(template, connectionFactory);
        return template;
    }
}
