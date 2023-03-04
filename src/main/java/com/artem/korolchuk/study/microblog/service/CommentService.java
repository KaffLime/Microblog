package com.artem.korolchuk.study.microblog.service;

import com.artem.korolchuk.study.microblog.entity.Comment;
import com.artem.korolchuk.study.microblog.request.CommentRequest;
import java.util.List;



public interface CommentService {
    void create(CommentRequest comment, int postId);
    List<Comment> readAll(int postId);
    Comment read(int postId, int commentId);
    boolean update(CommentRequest comment, int postId, int commentId);
    boolean delete(int postId, int commentId);
}
