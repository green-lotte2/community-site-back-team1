package kr.co.zeroPie.dto;

import kr.co.zeroPie.entity.ChatRecords;
import kr.co.zeroPie.repository.ChatRecordsRepository;
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
@ToString
public class ChatRoomDTO {

    private String roomId;
    private String name;
    private String stfNo;//방을 생성한 사용자 저장
    private Set<String> userIds = new HashSet<>(); // 사용자 ID를 저장
    private Set<WebSocketSession> sessions = new HashSet<>(); // 세션 ID와 WebSocketSession을 매핑


    @Builder
    public ChatRoomDTO(String roomId, String name,String stfNo) {
        this.roomId = roomId;
        this.name = name;
        this.stfNo = stfNo;
    }

    public ChatRoomDTO() {

    }

    public void handleActions(String userId, WebSocketSession session, ChatMessageDTO chatMessage, ChatService chatService) {



        log.info("여기 들어옴?......4 - session, chatMessage, chatService" + userId + "/" + "/" + chatMessage + "/" + chatService);

        if (chatMessage.getType().equals(ChatMessageDTO.MessageType.ENTER)) {
            if (!sessions.contains(session)) {
                sessions.add(session);
                chatService.addSession(userId, session);  // Ensure the session is added with the correct userId
                log.info("입장할때 무슨무슨 데이터가 들어오는지 보자 : "+chatMessage);

            }
        } else if (chatMessage.getType().equals(ChatMessageDTO.MessageType.NOMAL)) {//TALK 상태일때 sessions에서 제거가 될 수 있음(새로고침 시)
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
        } else if (chatMessage.getType().equals(ChatMessageDTO.MessageType.QUIT)) {

            //본인세션 없애기

            WebSocketSession beforeSession = chatService.getSessionById(userId);
            log.info("Removing session for userId={}", userId);

            if (beforeSession != null) {
                Iterator<WebSocketSession> iterator = sessions.iterator();
                while (iterator.hasNext()) {
                    WebSocketSession existingSession = iterator.next();
                    if (existingSession.equals(beforeSession)) {
                        iterator.remove();
                        chatService.removeSession(userId);
                        break;
                    }
                }
            }
        }else{
            log.info("여기는 토크상태임");
        }

        log.info("여기 들어옴?.......5");
        sendMessage(chatMessage, chatService);
    }

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


