package com.example.gnosis.models;

import java.util.Date;

public class Comment {

    private int id;
    private Post postId;
    private String content;
    private User userId;
    private Date createdAt;

    public Comment(Post postId, String content, User userId){
        this.postId = postId;
        this.content = content;
        this. userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Post getPostId() {
        return postId;
    }

    public void setPostId(Post postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
