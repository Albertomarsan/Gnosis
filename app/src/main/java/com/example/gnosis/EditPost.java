package com.example.gnosis;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gnosis.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditPost#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditPost extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText title;
    private TextInputEditText content;
    private Button btnEdit;
    private FirebaseFirestore db;
    String id;
    private ArrayList<String> postEditData;

    public EditPost() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditPost.
     */
    // TODO: Rename and change types and number of parameters
    public static EditPost newInstance(String param1, String param2) {
        EditPost fragment = new EditPost();
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
        final View view = inflater.inflate(R.layout.fragment_edit_post, container, false);

        db = FirebaseFirestore.getInstance();

        title = view.findViewById(R.id.inputTitle_edit);
        content = view.findViewById(R.id.inputContent_edit);
        btnEdit = view.findViewById(R.id.btnEditPost);

        Bundle bundle = this.getArguments();
        postEditData = bundle.getStringArrayList("postEditData");

        content.setText(postEditData.get(0));
        title.setText(postEditData.get(1));
        id = postEditData.get(2);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CollectionReference colRef = db.collection("Posts");
                colRef.whereEqualTo("id", postEditData.get(2)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<Object, String> map = new HashMap<>();
                                map.put("contenido", content.getText().toString());
                                map.put("titulo", title.getText().toString());
                                colRef.document(document.getId()).set(map, SetOptions.merge());
                            }

                            Toast.makeText(view.getContext(), "Post actualizado correctamente", Toast.LENGTH_SHORT).show();
                            TusPosts tusPosts = new TusPosts();
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(((ViewGroup)getView().getParent()).getId(), tusPosts).commit();
                        }
                    }
                });


            }
        });

        return view;
    }
}