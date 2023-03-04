package com.artem.korolchuk.study.microblog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "posts")
public class Post implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    
    @Column(name = "publicDate")
    private Timestamp publicDate;
    
    @Column(name = "header")
    private String header;
    
    @Column(name = "content")
    private String content;
    
    @Column(name = "likeCounter")
    private int likeCounter = 0;
    
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            },
            mappedBy = "likedPosts")
    private Set<Client> clientsWhoLiked = new HashSet<>();
    
    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    public Post() {
        
    }
    
    public Post(String publicDate, String header, String content) {
        this.publicDate = Timestamp.valueOf(publicDate);
        this.header = header;
        this.content = content;
    }

    public Post(String publicDate, String header, String content, Client client, List<Comment> comments) {
        this.publicDate = Timestamp.valueOf(publicDate);
        this.header = header;
        this.content = content;
        this.client = client;
        this.comments = comments;
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

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikeCounter() {
        return likeCounter;
    }

    public void setLikeCounter(int likeCounter) {
        this.likeCounter = likeCounter;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Client> getClientsWhoLiked() {
        return clientsWhoLiked;
    }

    public void setClientsWhoLiked(Set<Client> clientsWhoLiked) {
        this.clientsWhoLiked = clientsWhoLiked;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
