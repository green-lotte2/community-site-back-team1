package kr.co.zeroPie.service.chatting;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import kr.co.zeroPie.dto.ChatRoomDTO;
import kr.co.zeroPie.entity.ChatRoom;
import kr.co.zeroPie.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Scope("singleton")
public class ChatService {

    private final ObjectMapper objectMapper;
    private final ChatRoomRepository chatRoomRepository;
    private Map<String, ChatRoomDTO> chatRooms;

    @PostConstruct
    private void init() {
        log.info("되라.. 제발...");
    }

    public List<ChatRoomDTO> findAllRoom() {

        List<ChatRoom> BeforeList = chatRoomRepository.findAll();

        List<ChatRoomDTO> dtoList = BeforeList.stream()
                .map(ChatRoom::toDTO)
                .collect(Collectors.toList());

        return dtoList;

    }

    public ChatRoomDTO findRoomById(String roomId) {
        return chatRooms.get(roomId);
    }

    public ChatRoomDTO createRoom(String name) {

        String randomId = UUID.randomUUID().toString();

        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setRoomId(randomId);
        chatRoom.setName(name);
        chatRoomRepository.save(chatRoom);

        return chatRoom.toDTO();
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}