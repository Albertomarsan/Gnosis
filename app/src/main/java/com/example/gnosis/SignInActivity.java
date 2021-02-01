package com.example.gnosis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private CheckBox remember;
    private EditText username;
    private EditText password;
    private EditText email;
    private Button btnLogin;
    private Button btnRegister;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        auth = FirebaseAuth.getInstance();
        remember = (CheckBox) findViewById(R.id.checkRemember);
        username = (EditText) findViewById(R.id.inputUsername);
        password = (EditText) findViewById(R.id.inputPassword);
        email = (EditText)  findViewById(R.id.inputEmail);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        checkSharedReferences();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nuevoUser = username.getText().toString();
                String nuevoPass = password.getText().toString();
                String nuevoEmail = email.getText().toString();


                if(remember.isChecked()){
                    editor.putString(getString(R.string.checkbox), "True");
                    editor.apply();

                    editor.putString(getString(R.string.miUser), nuevoUser);
                    editor.apply();

                    editor.putString(getString(R.string.miPass), nuevoPass);
                    editor.apply();

                    editor.putString(getString(R.string.miEmail), nuevoEmail);
                    editor.apply();
                }else{
                    editor.putString(getString(R.string.checkbox), "False");
                    editor.apply();

                    editor.putString(getString(R.string.miUser), "");
                    editor.apply();

                    editor.putString(getString(R.string.miPass), "");
                    editor.apply();

                    editor.putString(getString(R.string.miEmail), "");
                    editor.apply();
                }

                authentication(nuevoEmail, nuevoPass);

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignInActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    private void checkSharedReferences(){
        String checkbox = preferences.getString(getString(R.string.checkbox), "False");
        String miUser = preferences.getString(getString(R.string.miUser), "");
        String miPass = preferences.getString(getString(R.string.miPass), "");
        String miEmail = preferences.getString(getString(R.string.miEmail), "");

        username.setText(miUser);
        password.setText(miPass);
        email.setText(miEmail);

        if(checkbox.equals("True")) {
            remember.setChecked(true);
            Intent i = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(i);
        }else
            remember.setChecked(false);
    }

    private void authentication(String email, String pass){
        auth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = auth.getCurrentUser();
                            Intent i = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}