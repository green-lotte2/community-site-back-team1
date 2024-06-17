package kr.co.zeroPie.controller.article;

import kr.co.zeroPie.dto.CommentDTO;
import kr.co.zeroPie.entity.Comment;
import kr.co.zeroPie.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    // 댓글 추가
    @PostMapping("/article/comment")
    public Comment comment(@RequestBody CommentDTO commentDTO) {
        log.info(commentDTO.toString());
        return commentService.insertComment(commentDTO);
    }

    // 특정 게시글의 모든 댓글 조회
    @GetMapping("/article/comment")
    public ResponseEntity<List<CommentDTO>> comments(@RequestParam("articleNo") int articleNo) {
        log.info(articleNo + "");
        return commentService.selectComment(articleNo);
    }

    // 댓글 수정
    @PutMapping("/article/comment/{commentNo}")
    public ResponseEntity<Comment> updateComment(@PathVariable int commentNo, @RequestBody CommentDTO commentDTO) {
        log.info("Updating comment with id: " + commentNo);
        Comment updatedComment = commentService.updateComment(commentNo, commentDTO);
        return ResponseEntity.ok(updatedComment);
    }

    // 댓글 삭제
    @DeleteMapping("/article/comment/{commentNo}")
    public ResponseEntity<Void> deleteComment(@PathVariable int commentNo) {
        log.info("삭제할 댓글의 번호 : " + commentNo);
        commentService.deleteComment(commentNo);
        return ResponseEntity.noContent().build();
    }
}
