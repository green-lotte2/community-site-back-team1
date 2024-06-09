package kr.co.zeroPie.dto;


import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {
    // 메시지  타입 : 입장, 채팅
    public enum MessageType{
        ENTER, TALK
    }

    private MessageType Type; // 메시지 타입
    private String roomId; // 방 번호
    private String sender; // 채팅을 보낸 사람
    private String message; // 메시지
}
