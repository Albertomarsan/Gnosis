package com.example.gnosis;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gnosis.models.Comment;
import com.example.gnosis.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailedPost#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailedPost extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView myRecycler;
    private CommentAdapter adaptador;
    private FirebaseFirestore bd;
    private Fragment thisFragment;
    String username_comment;

    // atributos del post
    private TextView title;
    private ImageView profilePic_post;
    private TextView createdAt;
    private TextView content;
    private TextView username;
    private String post_id;
    private TextView category_postDetailed;

    // atributos sección comentarios
    private ImageView profilePic_comment;
    private EditText editText;
    private Button btnEnviar;

    ArrayList<Comment> comments;

    public DetailedPost() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailedPost.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailedPost newInstance(String param1, String param2) {
        DetailedPost fragment = new DetailedPost();
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
        View view = inflater.inflate(R.layout.fragment_detailed_post, container, false);

        title = view.findViewById(R.id.title_post);
        profilePic_post = view.findViewById(R.id.profilePic_post);
        content = view.findViewById(R.id.content_post);
        createdAt = view.findViewById(R.id.created_at_post);
        username = view.findViewById(R.id.username_post);
        category_postDetailed = view.findViewById(R.id.category_postDetailed);

        profilePic_comment = view.findViewById(R.id.profilePic_comment_post);
        editText = view.findViewById(R.id.input_comment);
        btnEnviar = view.findViewById(R.id.btn_comment_post);

        Bundle bundle = this.getArguments();
        ArrayList<String> postData;
        postData = bundle.getStringArrayList("postData");

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final SharedPreferences.Editor editor = preferences.edit();
        username_comment = preferences.getString(getString(R.string.miUser), "");
        editor.apply();

        username.setText(postData.get(0));
        content.setText(postData.get(1));
        createdAt.setText(postData.get(2));
        post_id = postData.get(3);
        title.setText(postData.get(4));
        category_postDetailed.setText(postData.get(5));

        bd = FirebaseFirestore.getInstance();
        thisFragment = this;

        // ENVÍO DE COMENTARIO
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contenido = editText.getText().toString();
                Map<String, Object> map = new HashMap<>();

                Calendar calendar = Calendar.getInstance();
                String date;

                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                date = dateFormat.format(calendar.getTime());

                map.put("content", contenido);
                map.put("createdAt", date);
                map.put("postId", post_id);
                map.put("userPicture", "");
                map.put("username", username_comment);
                bd.collection("Comments").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getContext(), "Se ha enviado con éxito", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Error en la conexión a la bd: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        comments = new ArrayList<>();
        getCommentsByPostId(view, postData.get(3));

        return view;
    }

    private void getCommentsByPostId(final View view, String idPost) {

        bd.collection("Comments").whereEqualTo("postId", idPost).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                    comments.add(doc.toObject(Comment.class));
                }

                myRecycler = view.findViewById(R.id.recycler_comments);
                myRecycler.setHasFixedSize(true);

                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                myRecycler.setLayoutManager(layoutManager);

                adaptador = new CommentAdapter(comments, getContext());
                myRecycler.setAdapter(adaptador);

            }
        });



    }
}