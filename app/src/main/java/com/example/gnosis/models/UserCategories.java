package com.example.gnosis.models;

public class UserCategories {

    private String category_id;
    private String user_email;


    public UserCategories(){
    }

    public UserCategories(String category_id, String user_email){
        this.category_id = category_id;
        this.user_email = user_email;
    }




    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }





}
