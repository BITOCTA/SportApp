package com.bitocta.sportapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bitocta.sportapp.R;

import com.bitocta.sportapp.db.entity.Training;
import com.bitocta.sportapp.db.entity.User;
import com.bitocta.sportapp.viewmodel.UserViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.bitocta.sportapp.ui.MainActivity.toolbar;


public class ProgressFragment extends Fragment {

    private TextView activeTraining;
    private TextView days;
    private ImageView activeTrainingImage;
    private ProgressBar progressBar;
    private RecyclerView daysList;
    private LinearLayoutManager layoutManager;
    private DaysListAdapter daysListAdapter;
    private TextView progressComplete;

    public static Context context;

    private User user;

    private DatabaseReference firebaseDB;
    private DatabaseReference userRef;
    Training training;


    public static final String POSITION_TAG = "position";

    public ProgressFragment() {
        // Required empty public constructor
    }


    public static ProgressFragment getInstance() {
        ProgressFragment fragment = new ProgressFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar.setTitle(getResources().getString(R.string.progress));

        context = getContext();



        firebaseDB = FirebaseDatabase.getInstance().getReference();
        userRef = firebaseDB.child("user");


        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (isAdded()) {

                    user = dataSnapshot.getValue(User.class);
                    training = user.getActiveTraining();

                    if (training != null) {
                        progressBar.setVisibility(View.VISIBLE);
                        activeTraining.setText(training.getName());
                        Glide.with(getContext()).load(training.getImage_path()).apply(RequestOptions.circleCropTransform()).into(activeTrainingImage);
                        int currentProgress = (user.getDay() / training.getDays()) * 100;
                        progressBar.setProgress(currentProgress);


                        days.setText(R.string.days);
                        layoutManager = new LinearLayoutManager(context);

                        daysListAdapter = new DaysListAdapter();

                        daysList.setLayoutManager(layoutManager);
                        daysList.setAdapter(daysListAdapter);

                        daysListAdapter.updateDaysListItems(training.getSetsOfExercises(), user);


                        if (user.getDay() == 0) {
                            progressComplete.setText("0% complete");
                        } else {
                            int progress = (int) (user.getDay() / Double.valueOf(training.getDays()) * 100);
                            progressBar.setProgress(progress);
                            progressComplete.setText(progress + "% complete");
                        }


                        daysListAdapter.setOnItemClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
                                int position = viewHolder.getAdapterPosition();

                                Bundle bundle = new Bundle();

                                bundle.putInt(POSITION_TAG, position);

                                Fragment fragment = SpecificDayFragment.getInstance();
                                fragment.setArguments(bundle);

                                getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack(null).commit();

                                /*RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
                                double newDay = Double.valueOf(viewHolder.getAdapterPosition() + 1);


                                user.setDay((int) newDay);
                                userRef.setValue(user);

                                int progress = (int) (newDay / Double.valueOf(training.getDays()) * 100);
                                progressBar.setProgress(progress);
                                progressComplete.setText(progress + "% complete");
                                daysListAdapter.updateDaysListItems(training.getSetsOfExercises(), user);
                                daysListAdapter.notifyDataSetChanged();*/
                                //daysList.requestLayout();
                                //daysList.forceLayout();



                            }
                        });

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        progressBar.setVisibility(View.INVISIBLE);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_progress, container, false);
        activeTraining = view.findViewById(R.id.progress_active_training);
        activeTrainingImage = view.findViewById(R.id.progress_active_training_image);
        progressBar = view.findViewById(R.id.progress_bar);
        daysList = view.findViewById(R.id.days_list);
        days = view.findViewById(R.id.days);
        progressComplete = view.findViewById(R.id.progress_complete);


        return view;
    }

}
