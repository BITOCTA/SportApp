package com.bitocta.sportapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bitocta.sportapp.R;
import com.bitocta.sportapp.UserRepo;
import com.bitocta.sportapp.db.entity.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

public class StatisticsFragment  extends Fragment {

    private DatabaseReference firebaseDB;
    private DatabaseReference userRef;

    TextView totalTrainings;
    TextView totalCalories;
    TextView totalTime;
    TextView currentWeight;

    User user;

    public StatisticsFragment() {
        // Required empty public constructor
    }


    public static StatisticsFragment getInstance() {
        StatisticsFragment fragment = new StatisticsFragment();
        return fragment;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseDB = FirebaseDatabase.getInstance().getReference();
        userRef = UserRepo.getUserRef();

        MainActivity.toolbar.setTitle(getText(R.string.statistics));

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user  = dataSnapshot.getValue(User.class);
                totalTrainings.setText(String.valueOf(user.getHistoryOfTrainings().size()));
                totalCalories.setText(user.getTotalCalories()+ " "+ getText(R.string.kcal));
                totalTime.setText(user.getTotalMinutes()+" m");

                currentWeight.setText(user.getWeight() + " " + getText(R.string.kg));

                ArrayList<Date> dates = new ArrayList<>(user.getHistoryOfTrainings().values());
                Collections.sort(dates);
                Date lastDate = dates.get(dates.size()-1);

                Log.d("TEST", new SimpleDateFormat("dd.MM.yyyy").format(lastDate));



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        totalTrainings = view.findViewById(R.id.total_training_text);
        totalCalories = view.findViewById(R.id.total_kcal_text);
        totalTime = view.findViewById(R.id.total_time_text);
        currentWeight = view.findViewById(R.id.current_weight_text);


        return view;
    }
}
