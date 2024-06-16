package kr.co.zeroPie.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ToString
@Getter
@Setter
public class ChatMessageDTO {
    // 메시지  타입 : 입장, 채팅, 퇴장

    public enum MessageType{
        ENTER, TALK, QUIT, NOMAL
    }

    private MessageType type; // 메시지 타입
    private String roomId; // 방 번호
    private String sender; // 채팅을 보낸 사람
    private String message; // 메시지
    private String rdate;
    private String img;//사용자의 이미지를 담기

    // LocalDateTime을 받아서 rdate를 설정하는 생성자
    public ChatMessageDTO() {

        LocalDateTime rdate = LocalDateTime.now();

        this.rdate = formatDateTime(rdate);
    }

    // LocalDateTime을 문자열 포맷으로 변환하는 메서드
    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }
}
