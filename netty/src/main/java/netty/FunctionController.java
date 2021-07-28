package netty;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * 1, 编写handler，即FunctionController
 * 2，配置路由。参考WebFluxConfig
 * @author zhangbaisen
 * @createTime 2021/3/31 17:06
 */
@Component
public class FunctionController {
    public Mono<ServerResponse> hi(ServerRequest request){
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue("hi,ni hao"));
    }
}
