package netty.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangbaisen
 * @createTime 2021/3/31 17:21
 */
@Configuration
public class WebConfig {
    @Bean
    public HandlerMapping handlerMapping(){
        Map<String, WebSocketHandler> map = new HashMap<>();
        map.put("/path",new MyWebsocketHandler());
        int order = -1; // before annotated controllers
        return new SimpleUrlHandlerMapping(map,order);
    }
    
    @Bean
    public WebSocketHandlerAdapter webSocketHandlerAdapter(){
        return new WebSocketHandlerAdapter();
    }
}
