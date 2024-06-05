package kr.co.zeroPie.service.chatting;

import kr.co.zeroPie.config.Util;
import kr.co.zeroPie.dto.ChatMessageDTO;
import kr.co.zeroPie.dto.ChatRoom;
import kr.co.zeroPie.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.UUID;

import java.util.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class ChatService {

    private final ChatRepository chatRepository;

    public List<ChatRoom> findAll() {
        return chatRepository.findAll();
    }

    public ChatRoom findRoomById(String roomId) {
        return chatRepository.findById(roomId);
    }

    public ChatRoom createRoom(String name) {
        String roomId = UUID.randomUUID().toString();
        ChatRoom chatRoom = ChatRoom.of(roomId, name);
        chatRepository.save(roomId, chatRoom);
        return chatRoom;
    }

    public void handleAction(
            String roomId,
            WebSocketSession session,
            ChatMessageDTO chatMessage
    ) {
        ChatRoom room = findRoomById(roomId);

        if (isEnterRoom(chatMessage)) {
            room.join(session);
            chatMessage.setMessage(chatMessage.getSender() + "님 환영합니다.");
        }

        TextMessage textMessage = Util.Chat.resolveTextMessage(chatMessage);
        room.sendMessage(textMessage);
    }

    private boolean isEnterRoom(ChatMessageDTO chatMessage) {
        return chatMessage.getMessageType().equals(ChatMessageDTO.MessageType.ENTER);
    }

}
