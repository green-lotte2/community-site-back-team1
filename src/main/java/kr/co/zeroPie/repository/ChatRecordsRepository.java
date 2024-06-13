package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.ChatRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRecordsRepository extends JpaRepository<ChatRecords, Integer> {

    public List<ChatRecords> findByRoomId(String roomId);
}
