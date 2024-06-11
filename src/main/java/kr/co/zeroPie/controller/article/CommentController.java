package kr.co.zeroPie.controller.article;

import kr.co.zeroPie.dto.CommentDTO;
import kr.co.zeroPie.entity.Comment;
import kr.co.zeroPie.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/article/comment")
    public Comment comment(@RequestBody CommentDTO commentDTO) {
        log.info(commentDTO.toString());
        return commentService.insertComment(commentDTO);
    }

    @GetMapping("/article/comment")
    public ResponseEntity<List<CommentDTO>> comments(@RequestParam("articleNo") int articleNo) {
        log.info(articleNo + "");
        return commentService.selectComment(articleNo);
    }
}
