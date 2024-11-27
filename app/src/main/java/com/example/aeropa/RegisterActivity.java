package com.example.aeropa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.aeropa.databinding.ActivityRegisterBinding;
import com.example.aeropa.databinding.RegisterDatasheetBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class RegisterActivity extends BaseActivity {
    private ActivityRegisterBinding registerBinding;
    private RegisterDatasheetBinding dsBinding;
    private String email, password, userName, phone;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = user;
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(registerBinding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance(); // Inisialisasi FirebaseDatabase
        databaseReference = database.getReference("User"); // Referensi ke "User" node

        continueEmail();
        haveAccount();
    }

    private void haveAccount() {
        registerBinding.haveAccountRegisterTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void continueEmail() {
        registerBinding.continueRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = String.valueOf(registerBinding.emailRegisterET.getText());

                if(email.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Email address cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                dsBinding = RegisterDatasheetBinding.inflate(getLayoutInflater());
                setContentView(dsBinding.getRoot());



                dsBinding.createAccountRegisterdsBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        password = dsBinding.passwordRegisterdsTI.getText().toString().trim();
                        userName = dsBinding.nameRegisterdsTI.getText().toString().trim();
                        phone = dsBinding.phoneRegisterdsTI.getText().toString().trim();
                        if(userName.isEmpty() || password.isEmpty() || phone.isEmpty()){
                            Toast.makeText(RegisterActivity.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                            Log.d("RegisterActivity", "Password: " + password);
                            Log.d("RegisterActivity", "Name: " + userName);
                            Log.d("RegisterActivity", "Phone: " + phone);
                            return;
                        }else{
                            mAuth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                // Sign in success, update UI with the signed-in user's information
                                                FirebaseUser user = mAuth.getCurrentUser();
                                                Toast.makeText(RegisterActivity.this, "Authentication Success.",
                                                        Toast.LENGTH_SHORT).show();
                                                if(user != null){
                                                    String userId = user.getUid();  // Mendapatkan ID pengguna unik dari Firebase
                                                    HashMap<String, Object> userData = new HashMap<>();
                                                    userData.put("UserName", userName);
                                                    userData.put("email", email);
                                                    userData.put("password", password);
                                                    userData.put("phone", phone);
                                                    userData.put("bookingList", new HashMap<String, Object>());

                                                    databaseReference.child(userId).setValue(userData)
                                                            .addOnCompleteListener(task1 -> {
                                                                if (task1.isSuccessful()) {
                                                                    Log.d("Firebase", "User" + userId + " registered and added to database");
                                                                    // Redirect ke halaman utama atau dashboard
                                                                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                                                    finish();
                                                                } else {
                                                                    Log.e("Firebase", "Failed to add user data", task1.getException());
                                                                }
                                                            });
                                                }
                                            } else {
                                                // If sign in fails, display a message to the user.
                                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                });
            }
        });
    }
}
