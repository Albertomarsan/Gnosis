package com.example.gnosis.models;

import java.util.List;

public class Category {

    private int id;
    private String title;
    private String description;
    private List<Post> postList;
    private List<Category> categories;


    public Category(String title, String description){
        this.title = title;
        this.description = description;
    }

    public Category(){
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
