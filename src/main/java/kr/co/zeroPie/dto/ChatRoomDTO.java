package kr.co.zeroPie.dto;

import kr.co.zeroPie.service.chatting.ChatService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Getter
@Setter
@Builder
public class ChatRoomDTO {

    private String roomId;
    private String name;
    private Set<WebSocketSession> sessions;

    public ChatRoomDTO() {
        this.sessions = new HashSet<>();
    }

    // 인자가 있는 생성자
    public ChatRoomDTO(String roomId, String name, Set<WebSocketSession> sessions) {
        this.roomId = roomId;
        this.name = name;
        this.sessions = sessions;
    }

    public void handleActions(WebSocketSession session, ChatMessageDTO chatMessage, ChatService chatService) {
        if (chatMessage.getType().equals(ChatMessageDTO.MessageType.ENTER)) {
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
        }
        sendMessage(chatMessage, chatService);
    }

    public <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
    }
}


