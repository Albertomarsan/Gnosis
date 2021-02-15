package com.example.gnosis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView profileImage;
    private TextView cambiarImagen, txtUsername, postCount, commentCount;
    public static final int PICK_IMAGE = 1;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    // Create a storage reference from our app
    StorageReference storageRef = storage.getReference();
    StorageReference imagesRef;

    public Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImage = view.findViewById(R.id.profileImage);
        cambiarImagen = view.findViewById(R.id.editImage);
        txtUsername = view.findViewById(R.id.txtUsernameProfile);
        postCount = view.findViewById(R.id.postCount);
        commentCount = view.findViewById(R.id.commentCount);


        cambiarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, PICK_IMAGE );
            }
        });

        return view;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == PICK_IMAGE) {
            try {

                final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                final SharedPreferences.Editor editor = preferences.edit();
                String username = preferences.getString(getString(R.string.miUser), "");
                editor.apply();

                imagesRef = storageRef.child("images/" + username);

                InputStream inputStream = getContext().getContentResolver().openInputStream(data.getData());
                UploadTask uploadTask = imagesRef.putStream(inputStream);
                Toast.makeText(getContext(), "Imagen actualizada", Toast.LENGTH_SHORT).show();



            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}