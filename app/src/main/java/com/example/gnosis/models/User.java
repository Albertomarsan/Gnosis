package com.example.gnosis.models;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class User {



    private long id;
    private String name;
    private String email;
    private String password;
    private int profilePic;
    private List<Category> categoryList;
    private List<Post> postList;
    private List<Comment> commentList;
    private FirebaseFirestore db;


    public User(){
    }


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
