package com.example.minipeta1q3version12;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHelper {

    // IMPORTANT: Check the top of the Data tab in Firebase Console (Realtime Database).
    // Copy the exact URL (e.g. "https://minipeta1q3version-1-2-128d6-default-rtdb.firebaseio.com")
    // and paste it here if it's different.
    public static final String DATABASE_URL = "https://minipeta1q3version-1-2-128d6-default-rtdb.asia-southeast1.firebasedatabase.app/";

    public static DatabaseReference getUsersReference() {
        if (DATABASE_URL != null && !DATABASE_URL.trim().isEmpty()) {
            return FirebaseDatabase.getInstance(DATABASE_URL).getReference("users");
        } else {
            return FirebaseDatabase.getInstance().getReference("users");
        }
    }
}
