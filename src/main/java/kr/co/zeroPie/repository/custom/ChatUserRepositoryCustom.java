package kr.co.zeroPie.repository.custom;

import com.querydsl.core.Tuple;
import kr.co.zeroPie.entity.ChatRoom;

import java.util.List;

public interface ChatUserRepositoryCustom{

    public List<Tuple> findByRoom(String stfNo);

}
