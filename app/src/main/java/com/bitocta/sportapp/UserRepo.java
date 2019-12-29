package com.bitocta.sportapp;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserRepo {


    @Nullable
    public static DatabaseReference getUserRef() {
        DatabaseReference firebaseDB = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = null;
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser fuser = auth.getCurrentUser();
        if (fuser != null) {
            userRef = firebaseDB.child("users").child(fuser.getUid());
        }
        return userRef;
    }
}
