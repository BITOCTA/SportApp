package com.bitocta.sportapp.db;

import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDB {

    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }


}
