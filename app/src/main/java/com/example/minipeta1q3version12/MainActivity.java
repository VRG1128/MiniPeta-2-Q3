package com.example.minipeta1q3version12;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Connect XML components
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.loginButton);
        TextView tvRegister = findViewById(R.id.tvRegister);

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

            } else if ("GayLord123".equals(user) && "6767".equals(pass)) {

                Toast.makeText(this,
                        getString(R.string.welcome_user, user),
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();

            } else {

                Toast.makeText(this,
                        R.string.login_failed,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}