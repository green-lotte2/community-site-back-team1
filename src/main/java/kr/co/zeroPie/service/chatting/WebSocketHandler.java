package kr.co.zeroPie.service.chatting;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.zeroPie.dto.ChatMessageDTO;
import kr.co.zeroPie.dto.ChatRoomDTO;
import kr.co.zeroPie.dto.StfDTO;
import kr.co.zeroPie.entity.ChatRoom;
import kr.co.zeroPie.service.StfService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
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
    private final StfService stfService;

    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = (String) session.getAttributes().get("userId");

        log.info("userId = " + userId);

        if (userId != null) {
            // userId를 이용한 로직 수행
            log.info("1번으로 들어와야해");
            chatService.addSession(userId, session);
        } else {
            log.warn("User ID is missing in the session attributes.");
            session.close(CloseStatus.POLICY_VIOLATION);
        }
    }


    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        String userId = (String) session.getAttributes().get("userId");
        if (userId != null) {
            chatService.removeSession(userId);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("Received message payload: {}", payload);

        ChatMessageDTO chatMessage = objectMapper.readValue(payload, ChatMessageDTO.class);
        log.info("Parsed chat message: {}", chatMessage);

        String userId = getUserIdFromSession(session);

        if(chatMessage.getImg()==null || chatMessage.getImg().equals("")) {//보낸 메시지에 이미지가 없으면 아래와 같이 저장

            StfDTO stfDTO = stfService.getUserInfo(userId);

            String img = stfDTO.getStfImg();

            chatMessage.setImg(img);//유저 이미지 저장
        }

        ChatRoomDTO room = chatService.findRoomById(chatMessage.getRoomId());
        if (room != null) {
            room.handleActions(userId, session, chatMessage, chatService);
        } else {
            log.warn("Chat room not found for roomId: {}", chatMessage.getRoomId());
        }
    }

    private String getUserIdFromSession(WebSocketSession session) {

        log.info(session.getAttributes().get("userId").toString());

        String userId = (String) session.getAttributes().get("userId");

        log.info("받아온 id값 : "+userId);

        return userId != null ? userId.toString() : null;
    }
}
