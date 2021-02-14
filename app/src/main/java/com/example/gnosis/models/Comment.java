package com.example.gnosis.models;

import java.util.Date;

public class Comment {

    private String id;
    private String postId;
    private String content;
    private String username;
    private String createdAt;
    private int userPicture;

    public Comment(String postId, String content, String username){
        this.postId = postId;
        this.content = content;
        this. username = username;
    }

    public Comment(){
    }



    public int getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(int userPicture) {
        this.userPicture = userPicture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
