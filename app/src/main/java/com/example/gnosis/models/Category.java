package com.example.gnosis.models;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

public class Category {

    private int id;
    private String nombre;
    private String description;
    private String color;
    private boolean selected;
    private List<Post> postList;
    private ArrayList<Category> myCategories;
    private List<String> categoryIdList;
    private FirebaseFirestore bd;


    public Category(String nombre, String description){
        this.nombre = nombre;
        this.description = description;
    }

    public Category(String nombre){
        this.nombre = nombre;
    }

    public Category(){
    }






    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Post> getPostList() {
        return postList;
    }

    public ArrayList<Category> getMyCategories() {
        return myCategories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
