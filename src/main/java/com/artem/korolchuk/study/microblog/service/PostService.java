package com.artem.korolchuk.study.microblog.service;

import com.artem.korolchuk.study.microblog.entity.Post;
import com.artem.korolchuk.study.microblog.request.LikeRequest;
import com.artem.korolchuk.study.microblog.request.PostRequest;
import java.util.List;

public interface PostService {
    void create(PostRequest post);
    List<Post> readAll();
    Post read(int id);
    boolean update(PostRequest post, int id);
    boolean delete(int id);
    boolean like(LikeRequest like, int id);
    boolean removeLike(LikeRequest like, int id);
}
