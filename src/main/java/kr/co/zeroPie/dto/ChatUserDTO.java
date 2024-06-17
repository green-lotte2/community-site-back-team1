package kr.co.zeroPie.dto;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatUserDTO {

    private int id;
    private String roomId;//방번호
    private String stfNo;//방에 들어온 유저
    private String roomName;//조인 후 들어오는 값
}
