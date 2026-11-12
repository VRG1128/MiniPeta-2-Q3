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

        Button btnExit = findViewById(R.id.btn_exit);
        if (btnExit != null) {
            btnExit.setOnClickListener(v -> finish());
        }
    }
}
