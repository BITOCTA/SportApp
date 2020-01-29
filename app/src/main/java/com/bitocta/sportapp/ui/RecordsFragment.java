package com.bitocta.sportapp.ui;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitocta.sportapp.Firebase;
import com.bitocta.sportapp.R;
import com.bitocta.sportapp.UserRepo;
import com.bitocta.sportapp.db.entity.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class RecordsFragment extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference userRef;
    private LinearLayoutManager layoutManager;
    private RecordsListAdapter recordsListAdapter;

    public RecordsFragment() {
        // Required empty public constructor
    }

    public static RecordsFragment getInstance() {
        RecordsFragment fragment = new RecordsFragment();
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userRef = UserRepo.getUserRef();

        layoutManager = new LinearLayoutManager(getContext());

        recordsListAdapter = new RecordsListAdapter();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recordsListAdapter);


        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                HashMap<String, Date> historyOfTrainings = user.getHistoryOfTrainings();
                ArrayList<String> names = new ArrayList<>(historyOfTrainings.keySet());
                ArrayList<Date> dates = new ArrayList<>(historyOfTrainings.values());
                ArrayList<Pair<String,Date>> records  = new ArrayList<>();
                for (int i = 0; i < historyOfTrainings.size(); i++) {
                    records.add(new Pair<>(names.get(i),dates.get(i)));
                }

                recordsListAdapter.updateRecords(records);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_records, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_records);
        return view;
    }
}
