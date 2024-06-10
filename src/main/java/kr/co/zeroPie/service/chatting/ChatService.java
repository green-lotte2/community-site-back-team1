package kr.co.zeroPie.service.chatting;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import kr.co.zeroPie.dto.ChatMessageDTO;
import kr.co.zeroPie.dto.ChatRoomDTO;
import kr.co.zeroPie.entity.ChatRoom;
import kr.co.zeroPie.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;
    private final ChatRoomRepository chatRoomRepository;
    private final Map<String, ChatRoomDTO> chatRooms = new LinkedHashMap<>();

    @PostConstruct
    private void init() {
        // 초기화 코드
    }

    public List<ChatRoomDTO> findAllRoom() {
        List<ChatRoom> chatRoomList = chatRoomRepository.findAll();
        return chatRoomList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ChatRoomDTO convertToDto(ChatRoom chatRoom) {
        return ChatRoomDTO.builder()
                .roomId(chatRoom.getRoomId())
                .name(chatRoom.getName())
                .build();
    }

    public ChatRoomDTO findRoomById(String roomId) {
        Optional<ChatRoom> optRoom = chatRoomRepository.findById(roomId);
        if (optRoom.isPresent()) {
            ChatRoom chatRoom = optRoom.get();
            return ChatRoomDTO.builder()
                    .roomId(chatRoom.getRoomId())
                    .name(chatRoom.getName())
                    .build();
        } else {
            return null;
        }
    }

    public ChatRoomDTO createRoom(String name) {
        String randomId = UUID.randomUUID().toString();
        ChatRoomDTO chatRoomDTO = ChatRoomDTO.builder()
                .roomId(randomId)
                .name(name)
                .build();

        ChatRoom chatRoom = modelMapper.map(chatRoomDTO, ChatRoom.class);
        chatRoomRepository.save(chatRoom);

        chatRooms.put(randomId, chatRoomDTO);

        return chatRoomDTO;
    }

    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload();
        ChatMessageDTO chatMessage = objectMapper.readValue(payload, ChatMessageDTO.class);
        ChatRoomDTO room = chatRooms.get(chatMessage.getRoomId());
        if (room != null) {
            room.handleActions(session, chatMessage, this);
        }
    }

    public <T> void sendMessage(String roomId, T message) {
        ChatRoomDTO room = chatRooms.get(roomId);
        if (room != null) {
            room.sendMessage(message, this);
        }
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}

