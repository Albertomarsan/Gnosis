package com.example.gnosis.ui.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gnosis.CreatePost;
import com.example.gnosis.DetailedPost;
import com.example.gnosis.PostAdapter;
import com.example.gnosis.R;
import com.example.gnosis.models.Category;
import com.example.gnosis.models.Post;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ImageView btnImageCreatePost;
    private CreatePost createPost = new CreatePost();
    private RecyclerView myRecycler;
    private HomeAdapter adaptador;
    private FirebaseFirestore bd;
    ArrayList<Post> posts;
    private String userEmail;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        btnImageCreatePost = view.findViewById(R.id.addPostImage);

        btnImageCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(((ViewGroup)getView().getParent()).getId(), createPost).commit();
            }
        });

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final SharedPreferences.Editor editor = preferences.edit();
        userEmail = preferences.getString(getString(R.string.miEmail), "");
        editor.apply();


        bd = FirebaseFirestore.getInstance();
        posts = new ArrayList<>();
        getPostsByUsername(view, userEmail);

        return view;


    }

    private void getPostsByUsername(final View view, String email) {

        bd.collection("UserCategories").whereEqualTo("user_email", email)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                //OBTENEMOS LOS IDs DE LAS CATEGORÍAS DEL USUARIO
                final List<String> selectedIdCategories = new ArrayList<>();
                for (QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                    selectedIdCategories.add(doc.getString("category_id"));
                }


                bd.collection("Categories").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        final List<String> allCategories = new ArrayList<>();
                        for (QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                            allCategories.add(doc.getString("nombre"));
                        }

                        final List<String> selectedCategories = new ArrayList<>();
                        for(int i=0; i<selectedIdCategories.size(); i++){
                            for(int j=0; j<allCategories.size(); j++){
                                if((Integer.parseInt(selectedIdCategories.get(i))) == j+1){
                                    selectedCategories.add(allCategories.get(j));
                                }
                            }
                        }
                        if(selectedCategories.isEmpty()){
                            Toast.makeText(view.getContext(), "Seleccione alguna categoría para ver sus posts asociados", Toast.LENGTH_SHORT).show();
                        }
                        else{

                        bd.collection("Posts").whereIn("categoria", selectedCategories).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for (QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                                    posts.add(doc.toObject(Post.class));
                                }

                                myRecycler = view.findViewById(R.id.recycler_home);
                                myRecycler.setHasFixedSize(true);

                                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                myRecycler.setLayoutManager(layoutManager);

                                adaptador = new HomeAdapter(posts);
                                myRecycler.setAdapter(adaptador);

                                adaptador.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
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

                                        Bundle bundle = new Bundle();
                                        bundle.putStringArrayList("postData", postData);

                                        DetailedPost detailedPost = new DetailedPost();
                                        detailedPost.setArguments(bundle);
                                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                        transaction.replace(((ViewGroup)getView().getParent()).getId(), detailedPost).commit();
                                    }
                                });
                            }
                        });
                    }
                }
                });
            }

        });

    }
}