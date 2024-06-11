package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.Comment;
import kr.co.zeroPie.repository.custom.CommentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>, CommentRepositoryCustom {

    void deleteByArticleNo(int articleNo);
}
