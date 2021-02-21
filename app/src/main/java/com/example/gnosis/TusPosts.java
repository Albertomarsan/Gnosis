package com.example.gnosis;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.example.gnosis.models.Post;
import com.example.gnosis.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TusPosts#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TusPosts extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView myRecycler;
    private PostAdapter adaptador;
    private FirebaseFirestore bd;
    ArrayList<Post> posts;
    private String userName;

    public TusPosts() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TusPosts.
     */
    // TODO: Rename and change types and number of parameters
    public static TusPosts newInstance(String param1, String param2) {
        TusPosts fragment = new TusPosts();
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
        View view = inflater.inflate(R.layout.fragment_tus_posts, container, false);

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final SharedPreferences.Editor editor = preferences.edit();
        userName = preferences.getString(getString(R.string.miUser), "");
        editor.apply();


        bd = FirebaseFirestore.getInstance();
        posts = new ArrayList<>();
        getPostsByUsername(view, userName);

        return view;
    }


    public void getPostsByUsername(final View view, String username) {

        bd = FirebaseFirestore.getInstance();
        bd.collection("Posts").whereEqualTo("username", username).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                    posts.add(doc.toObject(Post.class));
                }

                myRecycler = view.findViewById(R.id.recyclerCat);
                myRecycler.setHasFixedSize(true);

                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                myRecycler.setLayoutManager(layoutManager);

                adaptador = new PostAdapter(posts, getContext());
                myRecycler.setAdapter(adaptador);

                adaptador.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int pos) {
                        // Vamos a DetailedPost pasándole el id del post

                        ArrayList<String> postData = new ArrayList<>();
                        postData.add(posts.get(pos).getUsername());
                        postData.add(posts.get(pos).getContenido());
                        postData.add(posts.get(pos).getCreado_el());
                        postData.add(posts.get(pos).getId());
                        postData.add(posts.get(pos).getTitulo());
                        postData.add(posts.get(pos).getCategoria());
                        // conseguir el id desde aquí y pasarlo

                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList("postData", postData);

                        DetailedPost detailedPost = new DetailedPost();
                        detailedPost.setArguments(bundle);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(((ViewGroup)getView().getParent()).getId(), detailedPost).commit();
                    }

                    @Override
                    public void onDeleteClick(final int pos) {

                        final CollectionReference ref = bd.collection("Posts");
                        Query query = ref.whereEqualTo("id", posts.get(pos).getId());
                        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for (DocumentSnapshot document : task.getResult()) {
                                        ref.document(document.getId()).delete();
                                    }
                                    posts.remove(pos);
                                    adaptador.notifyItemRemoved(pos);
                                }
                            }
                        });


                    }

                    @Override
                    public void onEditClick(int pos) {

                        ArrayList<String> postEditData = new ArrayList<>();
                        postEditData.add(posts.get(pos).getContenido());
                        postEditData.add(posts.get(pos).getTitulo());
                        postEditData.add(posts.get(pos).getId());

                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList("postEditData", postEditData);

                        EditPost editPost = new EditPost();
                        editPost.setArguments(bundle);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(((ViewGroup)getView().getParent()).getId(), editPost).commit();
                    }

                });

            }
        });
    }


}