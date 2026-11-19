package com.example.minipeta1q3version12;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Connect XML components
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.loginButton);
        TextView tvRegister = findViewById(R.id.tvRegister);

        reference = FirebaseHelper.getUsersReference();

        if (tvRegister != null) {
            tvRegister.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            });
        }

        // Login button function
        loginButton.setOnClickListener(v -> {
            String user = username.getText().toString().trim();
            String pass = password.getText().toString();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this,
                        R.string.please_enter_credentials,
                        Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(this, "Logging in...", Toast.LENGTH_SHORT).show();

            Handler timeoutHandler = new Handler(Looper.getMainLooper());
            Runnable timeoutRunnable = () -> {
                Toast.makeText(MainActivity.this, "Connection Timed Out! Ensure Database is created in Firebase Console & set to Test Mode.", Toast.LENGTH_LONG).show();
            };
            timeoutHandler.postDelayed(timeoutRunnable, 5000);

            // Check user credentials against Firebase Realtime Database
            reference.child(user).get().addOnCompleteListener(task -> {
                timeoutHandler.removeCallbacks(timeoutRunnable);
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if (snapshot != null && snapshot.exists()) {
                        String dbPassword = snapshot.child("password").getValue(String.class);
                        if (dbPassword != null && dbPassword.equals(pass)) {
                            Toast.makeText(MainActivity.this,
                                    getString(R.string.welcome_user, user),
                                    Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this,
                                    R.string.login_failed,
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this,
                                "User does not exist",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Exception e = task.getException();
                    String err = e != null ? e.getMessage() : "Unknown connection error";
                    Log.e("FirebaseDB", "Login fetch failed: " + err, e);
                    Toast.makeText(MainActivity.this,
                            "Database Error: " + err,
                            Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}