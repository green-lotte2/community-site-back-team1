package kr.co.zeroPie.repository;

import kr.co.zeroPie.dto.ChatRoomDTO;
import kr.co.zeroPie.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom,String> {

}
