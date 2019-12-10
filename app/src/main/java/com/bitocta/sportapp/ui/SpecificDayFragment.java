package com.bitocta.sportapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.ActionBarContainer;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bitocta.sportapp.R;
import com.bitocta.sportapp.db.entity.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SpecificDayFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private DayExercisesListAdapter dayExercisesListAdapter;

    private DatabaseReference firebaseDB;
    private DatabaseReference userRef;

    private User user;





    private int position;

    public static SpecificDayFragment getInstance() {
        SpecificDayFragment fragment = new SpecificDayFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            position = bundle.getInt(ProgressFragment.POSITION_TAG, 0);
        }
        firebaseDB = FirebaseDatabase.getInstance().getReference();
        userRef = firebaseDB.child("user");






        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);


                layoutManager = new LinearLayoutManager(getContext());

                dayExercisesListAdapter = new DayExercisesListAdapter();

                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(dayExercisesListAdapter);

                dayExercisesListAdapter.updateDaysListItems(user.getActiveTraining().getSetsOfExercises().get(position));
                dayExercisesListAdapter.notifyDataSetChanged();





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_specific_day, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_exercises);

        return view;
    }
}
