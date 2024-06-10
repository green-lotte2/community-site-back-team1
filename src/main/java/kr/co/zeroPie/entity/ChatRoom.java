package kr.co.zeroPie.entity;

import jakarta.persistence.*;
import kr.co.zeroPie.dto.ChatRoomDTO;
import lombok.*;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "chatroom")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String roomId;

    private String name;

    public ChatRoomDTO toDTO() {
        return ChatRoomDTO.builder()
                .roomId(this.roomId)
                .name(this.name)
                .build();
    }

}
