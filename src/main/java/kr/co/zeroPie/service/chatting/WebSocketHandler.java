package kr.co.zeroPie.service.chatting;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.zeroPie.dto.ChatMessageDTO;
import kr.co.zeroPie.dto.ChatRoomDTO;
import kr.co.zeroPie.entity.ChatRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
@Component
@EnableWebSocket
public class WebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);

        ChatMessageDTO chatMessage = objectMapper.readValue(payload, ChatMessageDTO.class);
        log.info("chatMessage {}", chatMessage.toString());
        ChatRoomDTO room = chatService.findRoomById(chatMessage.getRoomId());
        log.info("여기로 들어옴? ....5 room {}", room.toString());
        room.handleActions(session, chatMessage, chatService);
        log.info("여기로 들어옴?....6(끝!)");
    }
}
