package kr.co.zeroPie.controller.chatting;

import kr.co.zeroPie.dto.ChatRoomDTO;
import kr.co.zeroPie.dto.ChatUserDTO;
import kr.co.zeroPie.service.chatting.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Log4j2
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/chat")
    public ChatRoomDTO createRoom(@RequestBody String name) {

        log.info("name : " + name);

        return chatService.createRoom(name);
    }

    @GetMapping("/chat")
    public List<ChatRoomDTO> findAllRoom() {
        return chatService.findAllRoom();
    }

    @PostMapping("/findUser")
    public ResponseEntity<?> findUser(@RequestBody ChatUserDTO chatUserDTO){

        log.info("ChatController - chatUserDTO : " + chatUserDTO);

        return chatService.findUser(chatUserDTO);
    }

    @GetMapping("/saveUser")
    public void saveUser(@RequestParam("id")String id, @RequestParam("roomId")String roomId){

        log.info("saveUser - id : "+id);
        log.info("saveUser - roomId : "+roomId);

        chatService.saveUser(id,roomId);

    }
}