package netty.websocket;


import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author zhangbaisen
 * @createTime 2021/3/31 17:18
 */
public class MyWebsocketHandler implements WebSocketHandler {
    /**
     *
     * @param webSocketSession WebSocketSession
     *          1, Flux<WebSocketMessage> receive() 接受消息
     *          2, Mono<Void> send(Publisher<WebSocketMessage>) 发送消息
     */
    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        //1, 处理入站消息
        Mono<Void> input =  webSocketSession.receive() //1.1, 	Access the stream of inbound messages
                .doOnNext(message -> {
                    //1.2, Do something with each message.
                })
                .concatMap(message -> {
                    //1.3, Perform nested asynchronous operations that use the message content.
                    return null;
                })
                .then(); //1.4, Return a Mono<Void> that completes when receiving completes
        //2, 处理外发消息
        Flux<String> source = new Flux<String>() {
            @Override
            public void subscribe(CoreSubscriber<? super String> coreSubscriber) {
                
            }
        };
        Mono<Void> output = webSocketSession.send(source.map(webSocketSession::textMessage));
        //3, 结束
        return Mono.zip(input,output).then();
    }
}
