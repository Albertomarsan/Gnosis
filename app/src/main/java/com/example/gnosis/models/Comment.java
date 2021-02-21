package com.example.gnosis.models;

import java.util.Date;

public class Comment {

    private String postId;
    private String content;
    private String username;
    private String createdAt;
    private String userPicture;

    public Comment(String postId, String content, String username){
        this.postId = postId;
        this.content = content;
        this.username = username;
    }

    public Comment(){
    }




    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return username;
    }

    public void setUserId(User userId) {
        this.username = username;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
