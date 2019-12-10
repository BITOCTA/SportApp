package com.bitocta.sportapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Firebase {
    private DatabaseReference firebaseDB;

    public DatabaseReference getFirebaseDB() {
        return FirebaseDatabase.getInstance().getReference();
    }

}
