package kr.co.zeroPie.config;


import kr.co.zeroPie.handler.WebsocketHandler2;
import kr.co.zeroPie.service.chatting.WebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private static final Logger log = LoggerFactory.getLogger(WebSocketConfig.class);
    private final WebSocketHandler webSocketHandler;
    private final WebsocketHandler2 webSocketHandler2;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // ws://localhost:8080/ws 으로 요청이 들어오면 websocket 통신을 진행한다.
        // setAllowedOrigins("*")는 모든 ip에서 접속 가능하도록 해줌

        log.info("여기들어오나요?");

        registry.addHandler(webSocketHandler, "/ws")
                .setAllowedOrigins("*") // 모든 origin에서 접속 허용
                .addInterceptors(new HttpSessionHandshakeInterceptor());

        registry.addHandler(webSocketHandler2, "/doc").setAllowedOrigins("*");

    }

}
