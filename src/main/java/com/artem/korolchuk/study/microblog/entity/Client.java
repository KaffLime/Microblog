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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "clients")
public class Client implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    
    @Column(name = "login")
    private String login;
    
    @Column(name = "password")
    private String password;
    
    @JsonIgnore
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;
    
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            })
    @JoinTable(name = "likes_table",
            joinColumns = { @JoinColumn(name = "client_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "post_id", referencedColumnName = "id") })
    private Set<Post> likedPosts = new HashSet<>();
    
    @JsonIgnore
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    public Client() {
        
    }
    
    public Client(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Client(String login, String password, List<Post> posts, List<Comment> comments) {
        this.login = login;
        this.password = password;
        this.posts = posts;
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void addLikedPost(Post post) {
        this.likedPosts.add(post);
        post.getClientsWhoLiked().add(this);
    }
    
    public void removeLikedPost(int id) {
        Post post = this.likedPosts.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
        if (post != null) {
            this.likedPosts.remove(post);
            post.getClientsWhoLiked().remove(this);
        }
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
