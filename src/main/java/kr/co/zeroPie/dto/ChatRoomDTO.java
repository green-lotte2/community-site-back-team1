package kr.co.zeroPie.dto;

import kr.co.zeroPie.service.chatting.ChatService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Getter
@Setter
public class ChatRoomDTO {

    private String roomId;
    private String name;
    private Set<String> userIds = new HashSet<>(); // 사용자 ID를 저장
    private Set<WebSocketSession> sessions = new HashSet<>(); // 세션 ID와 WebSocketSession을 매핑


    @Builder
    public ChatRoomDTO(String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

    public void handleActions(String userId, WebSocketSession session, ChatMessageDTO chatMessage, ChatService chatService) {

        log.info("여기 들어옴?......4 - session, chatMessage, chatService" + userId + "/" + "/" + chatMessage + "/" + chatService);

        if (chatMessage.getType().equals(ChatMessageDTO.MessageType.ENTER)) {
            if (!sessions.contains(session)) {
                sessions.add(session);
                chatService.addSession(userId, session);  // Ensure the session is added with the correct userId
                chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
            }
        }else if (chatMessage.getType().equals(ChatMessageDTO.MessageType.TALK)) {//TALK 상태일때 sessions에서 제거가 될 수 있음(새로고침 시)
            WebSocketSession beforeSession = chatService.getSessionById(userId);
            log.info("Before session: {}", beforeSession);

            if (beforeSession == null) {
                log.info("Creating new session for userId={}", userId);
                sessions.add(session);
                chatService.addSession(userId, session);
            } else {
                log.info("Updating session for userId={}", userId);

                //이전 세션은 삭제
                Iterator<WebSocketSession> iterator = sessions.iterator();
                while (iterator.hasNext()) {
                    WebSocketSession existingSession = iterator.next();
                    if (existingSession.equals(beforeSession)) {
                        iterator.remove();
                        break;
                    }
                }

                // Add the new session
                sessions.add(session);

                // Update the session in ChatService
                chatService.addSession(userId, session);
            }
        }

        log.info("여기 들어옴?.......5");
        sendMessage(chatMessage, chatService);
    }

    /*
    구독자를 관리해야함 -> 채팅방에서 구독자들이 나가고(QUIT) 들어올때(ENTER)

    private List<WebSocketSession> subscribers = new ArrayList<>();

    public ChatRoom(String roomId) {
        this.roomId = roomId;
    }

    public void subscribe(WebSocketSession session) {
        subscribers.add(session);
    }

    public void unsubscribe(WebSocketSession session) {
        subscribers.remove(session);
    }

    public void sendMessageToSubscribers(Object message, ChatService chatService) {


     */

    public <T> void sendMessage(T message, ChatService chatService) {

        log.info("여기 들어옴?.......6" + message + "/" + chatService);

        log.info("sessions의 사이즈가 알고싶다 : " + sessions.size());
        log.info("sessions의 값이 알고싶다 : " + sessions);
        try {
            sessions.parallelStream().forEach(session -> {
                if (session.isOpen()) {
                    chatService.sendMessage(session, message);
                } else {
                    sessions.remove(session);
                }
            });
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }
}


