package kr.co.zeroPie.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.zeroPie.entity.ChatRoom;
import kr.co.zeroPie.entity.QChatRoom;
import kr.co.zeroPie.entity.QChatUser;
import kr.co.zeroPie.repository.custom.ChatUserRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Slf4j
@RequiredArgsConstructor
public class ChatUserRepositoryImpl implements ChatUserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    QChatRoom qChatRoom = QChatRoom.chatRoom;
    QChatUser qChatUser = QChatUser.chatUser;


    @Override
    public List<Tuple> findByRoom(String stfNo) {

        List<Tuple> roomList = jpaQueryFactory
                .select(qChatRoom.roomId, qChatRoom.name, qChatRoom.stfNo)
                .from(qChatRoom)
                .where(qChatRoom.roomId.in(
                        JPAExpressions.select(qChatRoom.roomId)//서브쿼리로 먼저 중복제거
                                .from(qChatRoom)
                                .leftJoin(qChatUser).on(qChatRoom.roomId.eq(qChatUser.roomId))
                                .where(qChatUser.stfNo.eq(stfNo).or(qChatRoom.stfNo.eq(stfNo)))
                                .distinct()
                ))
                .fetch();

        log.info("chatUserRepositoryImpl - roomNames : " + roomList);

        return roomList;
    }
}
