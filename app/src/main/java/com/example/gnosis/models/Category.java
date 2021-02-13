package com.example.gnosis.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;

import androidx.preference.PreferenceManager;

import com.example.gnosis.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Category {

    private int id;
    private String title;
    private String description;
    private List<Post> postList;
    private List<Category> myCategories;
    private List<String> categoryIdList;
    private FirebaseFirestore db;


    public Category(String title, String description){
        this.title = title;
        this.description = description;
    }

    public Category(){
    }


    public void getCategoriesIdByEmail(Context context){

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = preferences.edit();
        String userEmail = preferences.getString(context.getString(R.string.miEmail), "");
        editor.apply();

        db = FirebaseFirestore.getInstance();

        db.collection("UserCategories").whereEqualTo("user_email", userEmail)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot doc: queryDocumentSnapshots) {
                    categoryIdList.add((String) doc.get("category_id"));
                }
            }
        });
    }



    public void getCategoriesById(){

        db = FirebaseFirestore.getInstance();
        myCategories = new ArrayList<>();
        DocumentReference docRef;

        for (String id: categoryIdList) {
            docRef = db.collection("Categories").document(id);
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot document) {
                        myCategories.add(document.toObject(Category.class));
                }
            });
        }


    }



    public List<Post> getPostList() {
        return postList;
    }

    public List<Category> getMyCategories() {
        return myCategories;
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
