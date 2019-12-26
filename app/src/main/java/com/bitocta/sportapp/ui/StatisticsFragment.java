package com.bitocta.sportapp.ui;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;

public class StatisticsFragment  extends Fragment {

    private DatabaseReference firebaseDB;
    private DatabaseReference userRef;

    public StatisticsFragment() {
        // Required empty public constructor
    }


    public static StatisticsFragment getInstance() {
        StatisticsFragment fragment = new StatisticsFragment();
        return fragment;
    }
}
