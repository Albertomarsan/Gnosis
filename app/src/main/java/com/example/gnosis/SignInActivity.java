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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class SignInActivity extends AppCompatActivity {
    private static int GOOGLE_SIGN_IN = 100;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private CheckBox remember;
    private EditText username;
    private EditText password;
    private EditText email;
    private Button btnLogin;
    private Button btnRegister;
    private FirebaseAuth auth;
    private ImageView google;

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
        google = findViewById(R.id.googleIcon);

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

        google.setOnClickListener(new View.OnClickListener() {
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

                googleAuth(nuevoEmail, nuevoPass);

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

    private void googleAuth(String email, String pass) {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient googleClient = GoogleSignIn.getClient(this, gso);
        googleClient.signOut();
        startActivityForResult(googleClient.getSignInIntent(), GOOGLE_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if(account != null){
                    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Toast.makeText(SignInActivity.this, "Ha iniciado sesión con Google correctamente", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(i);
                        }
                    });
                }

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
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