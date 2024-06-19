package kr.co.zeroPie.controller.chatting;

import kr.co.zeroPie.dto.ChatRecordsDTO;
import kr.co.zeroPie.dto.ChatRoomDTO;
import kr.co.zeroPie.dto.ChatUserDTO;
import kr.co.zeroPie.entity.ChatRecords;
import kr.co.zeroPie.entity.ChatRoom;
import kr.co.zeroPie.entity.ChatUser;
import kr.co.zeroPie.entity.Stf;
import kr.co.zeroPie.service.chatting.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Log4j2
public class ChatController {

    private final ChatService chatService;

    //방 생성
    @PostMapping("/chat")
    public ChatRoomDTO createRoom(@RequestBody ChatRoomDTO chatRoomDTO) {

        log.info("controller - chatRoomDTO : "+chatRoomDTO);

        return chatService.createRoom(chatRoomDTO.getName(),chatRoomDTO.getStfNo());
    }

    //모든 방을 보여주기
    @GetMapping("/chat")
    public List<ChatRoom> findMyRoom(@RequestParam("stfNo")String stfNo) {

        return chatService.findMyRoom(stfNo);
    }


    //지금 입장한 유저가 원래 방에 있었던 유저인지 체크(입장멘트)
    @PostMapping("/findUser")
    public ResponseEntity<?> findUser(@RequestBody ChatUserDTO chatUserDTO){

        log.info("ChatController - chatUserDTO : " + chatUserDTO);

        return chatService.findUser(chatUserDTO);
    }


    //입장한 유저가 신규유저이면 방에 유저를 저장
    @GetMapping("/saveUser")
    public void saveUser(@RequestParam("id")String id, @RequestParam("roomId")String roomId){

        log.info("saveUser - id : "+id);
        log.info("saveUser - roomId : "+roomId);

        chatService.saveUser(id,roomId);

    }


    //입장한 유저가 신규유저이면 방에 유저를 저장
    @PostMapping("/saveUser")
    public List<Stf> saveUser(@RequestBody List<ChatUserDTO> chatUserDTOList){

        //log.info("멤버 추가 - Post_saveUser - chatUserDTOList : "+chatUserDTOList);

        //멤버의 이름을 리스트로 만들어서 반환하면 반환된 이름으로 입장하였습니다. 메시지 띄우기

        return chatService.addUser(chatUserDTOList);

    }




    //채팅내용 저장
    @PostMapping("/chatSave")
    public void chatSave(@RequestBody ChatRecordsDTO chatRecordsDTO){

        log.info("chatSave - chatRecordsDTO : "+chatRecordsDTO);

        chatService.saveMessage(chatRecordsDTO);

    }

    //채팅 내용 가져오기
    @GetMapping("/getMessage")
    public List<ChatRecordsDTO> getMessage(@RequestParam("roomId")String roomId){

        log.info("ChatController - getMessage - roomId : "+roomId);

        return chatService.findRoom(roomId);
    }

    
    //방 나가기
    @PostMapping("/leaveRoom")
    public void postLeaveRoom(@RequestBody ChatUserDTO chatUserDTO){

        log.info("방 나가기 : "+chatUserDTO);

        chatService.leaveRoom(chatUserDTO.getRoomId(),chatUserDTO.getStfNo());
    }

    @GetMapping("/deleteRoom")
    public int deleteRoom(@RequestParam("roomId")String roomId,@RequestParam("stfNo")String stfNo){

        log.info("deleteRoom - roomId : "+roomId);
        log.info("deleteRoom - stfNo : "+stfNo);

        int result = chatService.deleteRoom(roomId,stfNo);

        return result;

    }
    
    //채팅방에 있는 유저를 탐색. 만약 있다면 있다고 문구띄우게
    @GetMapping("/findUserList")
    public List<ChatUser> findUserList(@RequestParam("roomId")String roomId){

        log.info("findUserList - roomId : "+roomId);

        return chatService.findUserList(roomId);
    }

    @GetMapping("/getCount")
    public int getCountUser(@RequestParam("roomId")String roomId){

        log.info("getCountUser - roomId : "+roomId);

        return chatService.countStfNo(roomId);

    }
}