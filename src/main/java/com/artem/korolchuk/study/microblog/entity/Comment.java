package com.artem.korolchuk.study.microblog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Timestamp;


@Entity
@Table(name = "comments")
public class Comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    
    @Column(name = "publicDate")
    private Timestamp publicDate;
    
    @Column(name = "content")
    private String content;
    
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Comment() {
        
    }
    
    public Comment(Timestamp publicDate, String content) {
        this.publicDate = publicDate;
        this.content = content;
    }

    public Comment(Timestamp publicDate, String content, Client client, Post post) {
        this.publicDate = publicDate;
        this.content = content;
        this.client = client;
        this.post = post;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPublicDate() {
        return publicDate.toString();
    }

    public void setPublicDate(String publicDate) {
        this.publicDate = Timestamp.valueOf(publicDate);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
