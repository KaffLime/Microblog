package com.artem.korolchuk.study.microblog.controller;

import com.artem.korolchuk.study.microblog.entity.Comment;
import com.artem.korolchuk.study.microblog.request.CommentRequest;
import com.artem.korolchuk.study.microblog.service.CommentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/posts/{postId}/comments")
public class CommentController {
    
    @Autowired
    private CommentService commentService;
    
    @GetMapping("")
    public ResponseEntity<List<Comment>> read(@PathVariable("postId") int postId) {
        List<Comment> comments = commentService.readAll(postId);
        
        if (comments == null || comments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(comments, HttpStatus.OK);
        }
    }
    
    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> read(@PathVariable("postId") int postId, @PathVariable("commentId") int commentId) {
        Comment comment = commentService.read(postId, commentId);
       
        if (comment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(comment, HttpStatus.OK);
        }
    }
    
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CommentRequest comment, @PathVariable("postId") int postId) {
        commentService.create(comment, postId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @PutMapping("/{commentId}")
    public ResponseEntity<?> update(@RequestBody CommentRequest comment, @PathVariable("postId") int postId, @PathVariable("commentId") int commentId) {
        if (commentService.update(comment, postId, commentId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }
    
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> delete(@PathVariable("postId") int postId, @PathVariable("commentId") int commentId) {
        if (commentService.delete(postId, commentId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }
}
