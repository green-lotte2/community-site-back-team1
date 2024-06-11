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
public class ChatRoomDTO {

    private String roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoomDTO(String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

    public void handleActions(WebSocketSession session, ChatMessageDTO chatMessage, ChatService chatService) {

        log.info("여기 들어옴?......4 - session, chatMessage, chatService" + session+ "/"+ "/"+chatMessage+"/"+chatService);

        if (chatMessage.getType().equals(ChatMessageDTO.MessageType.ENTER)) {
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
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

        log.info("여기 들어옴?.......6"+message+"/"+chatService);

        log.info("sessions의 사이즈가 알고싶다 : "+sessions.size());
        log.info("sessions의 값이 알고싶다 : "+sessions);
        try{
            //for(WebSocketSession ws :sessions){
            //    if(ws.isOpen()){
                    sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
             //   }
            //}
        }catch (Exception e){
            log.info(e.getMessage());
        }
    }
}


