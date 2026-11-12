package com.example.minipeta1q3version12;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        Button btnRegister = findViewById(R.id.btnRegister);

        if (btnRegister != null) {
            btnRegister.setOnClickListener(v -> {
                String user = username != null ? username.getText().toString().trim() : "";
                String pass = password != null ? password.getText().toString() : "";

                if (user.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(this, R.string.please_enter_credentials, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
    }
}
