package netty;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author zhangbaisen
 * @createTime 2021/3/31 18:02
 */
@Configuration(proxyBeanMethods = false)
public class WebFluxConfig {
    
    @Bean
    public RouterFunction<ServerResponse> hiRouter(FunctionController handler){
        return RouterFunctions.route(
                        RequestPredicates.GET("/hi1").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        handler::hi
                );
    }
}
