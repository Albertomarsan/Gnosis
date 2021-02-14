package com.example.gnosis.models;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class Post {

    private String id;
    private String titulo;
    private String contenido;
    private String creado_el;
    private String categoria;
    private String username;
    private Comment commentId;
    private List<Post> posts;
    private FirebaseFirestore db;
    private boolean toRemove;

    public Post(String titulo, String contenido, String categoria){
        this.titulo = titulo;
        this.contenido = contenido;
        this.categoria = categoria;
    }

    public Post(){
    }


    public void toRemove(){
        this.toRemove = true;
    }

    public boolean isToRemove() {
        return toRemove;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getCreado_el() {
        return creado_el;
    }

    public void setCreado_el(String creado_el) {
        this.creado_el = creado_el;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Comment getCommentId() {
        return commentId;
    }

    public void setCommentId(Comment commentId) {
        this.commentId = commentId;
    }


}
