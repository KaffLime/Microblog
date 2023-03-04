package com.artem.korolchuk.study.microblog.service;

import com.artem.korolchuk.study.microblog.entity.Client;
import com.artem.korolchuk.study.microblog.entity.Post;
import com.artem.korolchuk.study.microblog.repository.ClientRepository;
import com.artem.korolchuk.study.microblog.repository.PostRepository;
import com.artem.korolchuk.study.microblog.request.LikeRequest;
import com.artem.korolchuk.study.microblog.request.PostRequest;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PostServiceImpl implements PostService {
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private ClientRepository clientRepository;
    
    @Override
    public void create(PostRequest post) {
        Optional<Client> optClient = clientRepository.findById(post.getClientId());
        
        if (!optClient.isPresent()) {
            return;
        }
        
        Post dbPost = new Post();
        
        Client dbClient = clientRepository.findById(post.getClientId()).get();
        
        dbPost.setPublicDate(post.getPublicDate());
        dbPost.setHeader(post.getHeader());
        dbPost.setContent(post.getContent());
        dbPost.setClient(dbClient);
        
        postRepository.save(dbPost);
    }
    
    @Override
    public List<Post> readAll() {
        return postRepository.findAll();
    }
    
    @Override
    public Post read(int id) {
        Optional<Post> optPost = postRepository.findById(id);
        
        if (!optPost.isPresent()) {
            return null;
        } else {
            return optPost.get();
        }
    }
    
    @Override
    public boolean update(PostRequest post, int id) {
        Optional<Post> optPost = postRepository.findById(id);
        
        if (!optPost.isPresent()) {
            return false;
        }
        
        Post dbPost = postRepository.findById(id).get();
        
        dbPost.setHeader(post.getHeader());
        dbPost.setContent(post.getContent());
        
        postRepository.save(dbPost);
        
        dbPost = postRepository.findById(id).get();
        
        return (dbPost.getHeader().equals(post.getHeader()) &&
                dbPost.getContent().equals(post.getContent()));
    }
    
    @Override
    public boolean like(LikeRequest like, int id) {
        Optional<Post> optPost = postRepository.findById(id);
        Optional<Client> optClient = clientRepository.findById(like.getClientId());
        
        if (!optPost.isPresent() || !optClient.isPresent()) {
            return false;
        }
        
        Post dbPost = postRepository.findById(id).get();
        Client clientWhoLiked = clientRepository.findById(like.getClientId()).get();
        
        int likeCounter = dbPost.getClientsWhoLiked().size();
        
        clientWhoLiked.addLikedPost(dbPost);
        
        dbPost.setLikeCounter(dbPost.getClientsWhoLiked().size());
        
        postRepository.save(dbPost);
        
        dbPost = postRepository.findById(id).get();
        
        return (dbPost.getLikeCounter() == likeCounter + 1);
    }
    
    @Override
    public boolean removeLike(LikeRequest like, int id) {
        Optional<Post> optPost = postRepository.findById(id);
        Optional<Client> optClient = clientRepository.findById(like.getClientId());
        
        if (!optPost.isPresent() || !optClient.isPresent()) {
            return false;
        }
        
        Post dbPost = postRepository.findById(id).get();
        Client clientWhoRemovedLike = clientRepository.findById(like.getClientId()).get();
        
        int likeCounter = dbPost.getClientsWhoLiked().size();
        
        clientWhoRemovedLike.removeLikedPost(dbPost.getId());
        
        dbPost.setLikeCounter(dbPost.getClientsWhoLiked().size());
        
        postRepository.save(dbPost);
        
        dbPost = postRepository.findById(id).get();
        
        return (dbPost.getLikeCounter() == likeCounter - 1);
    }
    
    @Override
    public boolean delete(int id) {
        Optional<Post> optPost = postRepository.findById(id);
        
        if (!optPost.isPresent()) {
            return false;
        }
        
        postRepository.deleteById(id);
        return (!postRepository.findById(id).isPresent());
    }
}
