package com.example.minipeta1q3version12;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        Button btnAttendanceList = findViewById(R.id.btn_attendance_list);
        if (btnAttendanceList != null) {
            btnAttendanceList.setOnClickListener(v -> {
                Intent intent = new Intent(DashboardActivity.this, AttendanceTrackerActivity.class);
                startActivity(intent);
            });
        }

        Button btnLogout = findViewById(R.id.btn_class_list2);
        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> {
                SessionManager sessionManager = new SessionManager(DashboardActivity.this);
                sessionManager.logout();
                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            });
        }

        Button btnExit = findViewById(R.id.btn_exit);
        if (btnExit != null) {
            btnExit.setOnClickListener(v -> finish());
        }
    }
}
