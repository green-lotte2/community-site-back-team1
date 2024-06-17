package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.ChatRoom;
import kr.co.zeroPie.entity.ChatUser;
import kr.co.zeroPie.repository.custom.ChatUserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatUserRepository extends JpaRepository<ChatUser,Integer>, ChatUserRepositoryCustom {

    public int countByRoomIdAndStfNo(String roomId, String stfNo);
    public void deleteById(int id);
    public List<ChatUser> findByRoomIdAndStfNo(String roomId, String stfNo);
    public void deleteByRoomId(String roomId);

    public List<ChatUser> findByRoomId(String roomId);

    public int countStfNoByRoomId(String roomId);

}
