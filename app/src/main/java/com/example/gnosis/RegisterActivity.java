package com.example.gnosis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore database;
    private String newUser;
    private String newPassword;
    private String newMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        database = FirebaseFirestore.getInstance();

        final EditText username = (EditText) findViewById(R.id.username);
        final EditText mail = (EditText) findViewById(R.id.email);
        final EditText password = (EditText) findViewById(R.id.password);
        Button btnRegister = (Button) findViewById(R.id.btnSubmit);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 newUser = username.getText().toString();
                 newPassword = password.getText().toString();
                 newMail = mail.getText().toString();

                if(!newUser.isEmpty() && !newPassword.isEmpty() && !newMail.isEmpty()){
                    if(newPassword.length() >= 6){
                        auth.createUserWithEmailAndPassword(newMail, newPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Registrado con éxito ", Toast.LENGTH_SHORT).show();
                                    registerUser();
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Problema al registrar usuario", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }else{
                        Toast.makeText(RegisterActivity.this, "El password debe tener mínimo 6 caracteres", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(RegisterActivity.this, "Faltan campos por completar", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void registerUser(){
        Map<String, Object> map = new HashMap<>();
        map.put("username", newUser);
        map.put("password", newPassword);
        map.put("email", newMail);
        database.collection("Users").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(RegisterActivity.this, "Se ha registrado con éxito", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(RegisterActivity.this, SignInActivity.class);
                startActivity(i);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "Error en el registro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}