package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.ChatRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRecordsRepository extends JpaRepository<ChatRecords, Integer> {

    //지정된 방에 저장된 메시지내용을 불러오기
    public List<ChatRecords> findByRoomId(String roomId);

    //룸아이디와 일치하는것 다 삭제
    public void deleteByRoomId(String roomId);
}
