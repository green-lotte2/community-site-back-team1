package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatUserRepository extends JpaRepository<ChatUser,Integer> {

    public int countByRoomIdAndStfNo(String roomId, String stfNo);

}
