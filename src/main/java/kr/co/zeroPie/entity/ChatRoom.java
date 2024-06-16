package kr.co.zeroPie.entity;

import jakarta.persistence.*;
import kr.co.zeroPie.dto.ChatRoomDTO;
import lombok.*;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;


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
    private String roomId;

    private String name;

    private String stfNo;

    @Transient
    private Set<WebSocketSession> sessions = new HashSet<>();

}
