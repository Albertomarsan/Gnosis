package com.example.gnosis;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gnosis.models.Category;
import com.example.gnosis.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreatePost#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreatePost extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FirebaseFirestore db;
    private Spinner spinnerCategory;
    private EditText title;
    private TextInputEditText content;
    private Button btnCreatePost;

    public CreatePost() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreatePost.
     */
    // TODO: Rename and change types and number of parameters
    public static CreatePost newInstance(String param1, String param2) {
        CreatePost fragment = new CreatePost();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_post, container, false);

        db = FirebaseFirestore.getInstance();

        // Obtenemos las categorias y las almacenamos en el spinner
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        getAllCategories();

        // Guardamos el post
        title = view.findViewById(R.id.inputTitle);
        content = view.findViewById(R.id.inputContent);
        btnCreatePost = view.findViewById(R.id.btnCreatePost);

        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPost();
            }
        });


        return view;
    }

    private void createPost() {

        Date currentDate = Calendar.getInstance().getTime();
        String titulo = title.getText().toString();
        String contenido = content.getText().toString();
        String categoria = spinnerCategory.getSelectedItem().toString();
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final SharedPreferences.Editor editor = preferences.edit();
        String userName = preferences.getString(getString(R.string.miUser), "");
        editor.apply();

        Map<String, Object> map = new HashMap<>();
        map.put("titulo", titulo);
        map.put("contenido", contenido);
        map.put("categoría", categoria);
        map.put("username", userName);
        map.put("creado el", currentDate.toString());
        db.collection("Posts").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getContext(), "Se ha creado con éxito", Toast.LENGTH_SHORT).show();
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(((ViewGroup)getView().getParent()).getId(), homeFragment).commit();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error en la conexión a la bd: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getAllCategories(){
       db.collection("Categories").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
           @Override
           public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

               List<String> lista = new ArrayList<>();
                   for (QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                       lista.add(doc.getString("nombre"));
                   }

                   String array[] = new String[lista.size()];

                   for(int i=0; i<array.length; i++){
                       array[i] = lista.get(i);
                   }

                   ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(
                           getContext(), android.R.layout.simple_spinner_dropdown_item, array);
                   spinnerCategory.setAdapter(spinnerArrayAdapter);

           }
       });
    }


}