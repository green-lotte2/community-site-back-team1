package kr.co.zeroPie.service;

import com.querydsl.core.Tuple;
import kr.co.zeroPie.dto.CommentDTO;
import kr.co.zeroPie.entity.Comment;
import kr.co.zeroPie.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    // 댓글 추가
    public Comment insertComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setArticleNo(commentDTO.getArticleNo());
        comment.setStfNo(commentDTO.getStfNo());
        comment.setCommentCnt(commentDTO.getCommentCnt());
        // Comment 엔티티에 필요한 나머지 속성들을 설정
        Comment saveComment = commentRepository.save(comment);

        return saveComment;
    }

    // 특정 게시글의 모든 댓글 조회
    public ResponseEntity<List<CommentDTO>> selectComment(int articleNo) {
        List<Tuple> commentList = commentRepository.findByArticleNo(articleNo);

        List<CommentDTO> commentDTOList = commentList.stream()
                .map(entity -> {
                    Comment comment = entity.get(0, Comment.class);
                    CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
                    commentDTO.setStfName(entity.get(1, String.class));
                    commentDTO.setStfImg(entity.get(2, String.class));
                    commentDTO.setStfNo(entity.get(3, String.class));
                    return commentDTO;
                })
                .toList();

        log.info(commentDTOList.toString());

        return ResponseEntity.status(HttpStatus.OK).body(commentDTOList);
    }

    // 댓글 수정
    public Comment updateComment(int commentNo, CommentDTO commentDTO) {
        Comment comment = commentRepository.findById(commentNo)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setCommentCnt(commentDTO.getCommentCnt());
        return commentRepository.save(comment);
    }

    // 댓글 삭제
    public void deleteComment(int commentNo) {
        commentRepository.deleteById(commentNo);
    }
}
