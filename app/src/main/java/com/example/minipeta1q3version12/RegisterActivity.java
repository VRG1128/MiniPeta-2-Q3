package com.example.minipeta1q3version12;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        Button btnRegister = findViewById(R.id.btnRegister);

        reference = FirebaseHelper.getUsersReference();

        if (btnRegister != null) {
            btnRegister.setOnClickListener(v -> {
                String user = username != null ? username.getText().toString().trim() : "";
                String pass = password != null ? password.getText().toString() : "";

                if (user.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(this, R.string.please_enter_credentials, Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(this, "Registering...", Toast.LENGTH_SHORT).show();

                Handler timeoutHandler = new Handler(Looper.getMainLooper());
                Runnable timeoutRunnable = () -> {
                    Toast.makeText(RegisterActivity.this, "Connection Timed Out! Ensure Database is created in Firebase Console & set to Test Mode.", Toast.LENGTH_LONG).show();
                };
                timeoutHandler.postDelayed(timeoutRunnable, 5000);

                // Save user credentials into Firebase Realtime Database
                reference.child(user).get().addOnCompleteListener(task -> {
                    timeoutHandler.removeCallbacks(timeoutRunnable);
                    if (task.isSuccessful()) {
                        DataSnapshot snapshot = task.getResult();
                        if (snapshot != null && snapshot.exists()) {
                            Toast.makeText(RegisterActivity.this, "User already exists!", Toast.LENGTH_SHORT).show();
                        } else {
                            User newUser = new User(user, pass);
                            reference.child(user).setValue(newUser).addOnCompleteListener(saveTask -> {
                                if (saveTask.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, DashboardActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Exception e = saveTask.getException();
                                    String err = e != null ? e.getMessage() : "Unknown save error";
                                    Log.e("FirebaseDB", "Save failed: " + err, e);
                                    Toast.makeText(RegisterActivity.this, "Save Failed: " + err, Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    } else {
                        Exception e = task.getException();
                        String err = e != null ? e.getMessage() : "Unknown connection error";
                        Log.e("FirebaseDB", "Fetch failed: " + err, e);
                        Toast.makeText(RegisterActivity.this, "Database Error: " + err, Toast.LENGTH_LONG).show();
                    }
                });
            });
        }
    }
}
