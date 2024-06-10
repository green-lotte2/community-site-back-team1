package kr.co.zeroPie.controller.chatting;

import kr.co.zeroPie.dto.ChatRoomDTO;
import kr.co.zeroPie.service.chatting.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Log4j2
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/chat")
    public ChatRoomDTO createRoom(@RequestBody Map<String, String> request) {

        String name = request.get("name");

        log.info("name : " + name);

        return chatService.createRoom(name);
    }

    @GetMapping("/chat")
    public List<ChatRoomDTO> findAllRoom() {
        return chatService.findAllRoom();
    }

}