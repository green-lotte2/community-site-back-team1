package kr.co.zeroPie.config;


import kr.co.zeroPie.handler.WebsocketHandler2;
import kr.co.zeroPie.service.chatting.WebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketHandler webSocketHandler;
    private final WebsocketHandler2 webSocketHandler2;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // ws://localhost:8080/ws 으로 요청이 들어오면 websocket 통신을 진행한다.
        // setAllowedOrigins("*")는 모든 ip에서 접속 가능하도록 해줌
        registry.addHandler(webSocketHandler, "/ws").setAllowedOrigins("*");
        registry.addHandler(webSocketHandler2, "/testaa").setAllowedOrigins("*");
    }

}
