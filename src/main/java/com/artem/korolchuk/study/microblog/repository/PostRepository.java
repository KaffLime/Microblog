package com.artem.korolchuk.study.microblog.repository;

import com.artem.korolchuk.study.microblog.entity.Post;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Optional<Post> findByClientId(int clientId);
}
