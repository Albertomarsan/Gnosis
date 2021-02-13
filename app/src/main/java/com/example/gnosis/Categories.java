package com.example.gnosis;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.gnosis.models.Category;
import com.example.gnosis.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Categories#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Categories extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView myRecycler;
    private CategoryAdapter adaptador;
    ArrayList<Category> categories;
    private FirebaseFirestore bd;
    private Button setUserCategories;

    public Categories() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Categories.
     */
    // TODO: Rename and change types and number of parameters
    public static Categories newInstance(String param1, String param2) {
        Categories fragment = new Categories();
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
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final SharedPreferences.Editor editor = preferences.edit();
        String userEmail = preferences.getString(getString(R.string.miEmail), "");
        editor.apply();

        bd = FirebaseFirestore.getInstance();
        categories = new ArrayList<>();
        getAllCategories(userEmail, view);

        return view;
    }


    public void getAllCategories(String userEmail, final View view){

        bd.collection("UserCategories").whereEqualTo("user_email", userEmail)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                //OBTENEMOS LOS IDs DE LAS CATEGORÍAS DEL USUARIO
                final List<String> lista = new ArrayList<>();
                for (QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                    lista.add(doc.getString("category_id"));
                }


                //OBTENEMOS LAS CATEGORÍAS ENTERAS A PARTIR DE ESOS IDs
                DocumentReference docRef;

                bd.collection("Categories").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot doc: queryDocumentSnapshots){
                            categories.add(doc.toObject(Category.class));
                        }
                        List<Category> selectedCategories = new ArrayList<>();
                        for(int i=0; i<lista.size(); i++){
                            for(int j=0; j<categories.size(); j++){
                                if((Integer.parseInt(lista.get(i))) == j){
                                    categories.get(j).setSelected(true);
                                }
                            }
                        }

                        myRecycler = view.findViewById(R.id.recyclerCat);
                        myRecycler.setHasFixedSize(true);

                        myRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));

                        adaptador = new CategoryAdapter(categories, getContext());
                        myRecycler.setAdapter(adaptador);

                        adaptador.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int pos) {
                                if(categories.get(pos).isSelected()) {
                                    categories.get(pos).setSelected(false);
                                    adaptador.notifyItemChanged(pos);
                                }
                                else {
                                    categories.get(pos).setSelected(true);
                                    adaptador.notifyItemChanged(pos);
                                }
                            }
                        });

                        setUserCategories = view.findViewById(R.id.btnSetUserCategories);
                        setUserCategories.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //PRIMERO BORRAMOS LAS CATEGORIAS DEL USUARIO SI ESTÁN DESMARCADAS

                            }
                        });

                    }
                });
            }
        });
    }



}