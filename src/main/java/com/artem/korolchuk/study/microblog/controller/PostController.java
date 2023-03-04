package com.artem.korolchuk.study.microblog.controller;

import com.artem.korolchuk.study.microblog.entity.Post;
import com.artem.korolchuk.study.microblog.request.LikeRequest;
import com.artem.korolchuk.study.microblog.request.PostRequest;
import com.artem.korolchuk.study.microblog.service.PostService;
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
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PostController {
    
    @Autowired
    private PostService postService;
    
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> read() {
        List<Post> posts = postService.readAll();
        
        if (posts == null || posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(posts, HttpStatus.OK);
        }
    }
    
    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> read(@PathVariable("id") int id) {
        Post post = postService.read(id);
        
        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(post, HttpStatus.OK);
        }
    }
    
    @PostMapping("/posts")
    public ResponseEntity<?> create(@RequestBody PostRequest post) {
        postService.create(post);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @PutMapping("/posts/{id}")
    public ResponseEntity<?> update(@RequestBody PostRequest post, @PathVariable("id") int id) {
        if (postService.update(post, id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }
    
    @PutMapping("/posts/{id}/like")
    public ResponseEntity<?> like(@RequestBody LikeRequest like, @PathVariable("id") int id) {
        if (postService.like(like, id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }
    
    @PutMapping("/posts/{id}/removeLike")
    public ResponseEntity<?> removeLike(@RequestBody LikeRequest like, @PathVariable("id") int id) {
        if (postService.removeLike(like, id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }
    
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        if (postService.delete(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }
}
