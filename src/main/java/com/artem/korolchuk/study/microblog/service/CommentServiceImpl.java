package com.artem.korolchuk.study.microblog.service;

import com.artem.korolchuk.study.microblog.entity.Client;
import com.artem.korolchuk.study.microblog.entity.Comment;
import com.artem.korolchuk.study.microblog.entity.Post;
import com.artem.korolchuk.study.microblog.repository.ClientRepository;
import com.artem.korolchuk.study.microblog.repository.CommentRepository;
import com.artem.korolchuk.study.microblog.repository.PostRepository;
import com.artem.korolchuk.study.microblog.request.CommentRequest;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommentServiceImpl implements CommentService {
    
    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private ClientRepository clientRepository;
    
    @Override
    public void create(CommentRequest comment, int postId) {
        Optional<Post> optPost = postRepository.findById(postId);
        Optional<Client> optClient = clientRepository.findById(comment.getClientId());
        
        if (!optClient.isPresent() || !optPost.isPresent()) {
            return;
        }
        
        Post dbPost = postRepository.findById(postId).get();
        Client dbClient = clientRepository.findById(comment.getClientId()).get();
        
        Comment dbComment = new Comment();
        
        dbComment.setPublicDate(comment.getPublicDate());
        dbComment.setContent(comment.getContent());
        dbComment.setClient(dbClient);
        dbComment.setPost(dbPost);
        
        commentRepository.save(dbComment);
    }
    
    @Override
    public List<Comment> readAll(int postId) {
        return commentRepository.findByPostId(postId);
    }
    
    @Override
    public Comment read(int postId, int commentId) {
        Optional<Comment> optComment = commentRepository.findById(commentId);
        
        if (!optComment.isPresent()) {
            return null;
        } else {
            return optComment.get();
        }
    }
    
    @Override
    public boolean update(CommentRequest comment, int postId, int commentId) {
        Optional<Comment> optComment = commentRepository.findById(commentId);
        
        if (!optComment.isPresent()) {
            return false;
        }
        
        Comment dbComment = commentRepository.findById(commentId).get();
        
        dbComment.setContent(comment.getContent());
        
        commentRepository.save(dbComment);
        
        dbComment = commentRepository.findById(commentId).get();
        
        return (dbComment.getContent().equals(comment.getContent()));
    }
    
    @Override
    public boolean delete(int postId, int commentId) {
        Optional<Comment> optComment = commentRepository.findById(commentId);
        
        if (!optComment.isPresent()) {
            return false;
        }
        
        commentRepository.deleteById(commentId);
        return (!commentRepository.findById(commentId).isPresent());
    }
}
