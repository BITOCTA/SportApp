package com.bitocta.sportapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bitocta.sportapp.R;
import com.bitocta.sportapp.db.entity.Plan;
import com.bitocta.sportapp.db.entity.Training;
import com.bitocta.sportapp.db.entity.User;
import com.bitocta.sportapp.viewmodel.TrainingViewModel;
import com.bitocta.sportapp.viewmodel.UserViewModel;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static com.bitocta.sportapp.ui.MainActivity.toolbar;

public class SpecificPlanFragment extends Fragment {
    private TextView description;
    private TextView additionalInfo;
    private TextView name;
    private ImageView image;
    int position;
    String trainingName;
    private TrainingViewModel trainingViewModel;

    private ExtendedFloatingActionButton startPlan;

    private DatabaseReference firebaseDB;
    private DatabaseReference userRef;
    private DatabaseReference trainingsRef;

    private User user;
    private Training training;


    public static SpecificPlanFragment getInstance() {
        SpecificPlanFragment fragment = new SpecificPlanFragment();
        return fragment;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            position = bundle.getInt(PlansFragment.POSITION_TAG, 0);
            trainingName = bundle.getString(PlansFragment.PLAN_NAME_TAG,"");
        }

        firebaseDB = FirebaseDatabase.getInstance().getReference();
        userRef = firebaseDB.child("user");
        trainingsRef = firebaseDB.child("trainings").child(trainingName);

        toolbar.setTitle(getString(R.string.plan_overview));



        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        trainingViewModel = ViewModelProviders.of(this).get(TrainingViewModel.class);


        View navView = MainActivity.navigationView.getHeaderView(0);
        TextView currentProgram = navView.findViewById(R.id.nav_current_program);

        trainingsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                training = dataSnapshot.getValue(Training.class);
                name.setText(training.getName());
                description.setText(training.getDescription());
                additionalInfo.setText(training.getDays() + " days | "+ training.getLevel()+" | "+training.getDaysAWeek()+" days a week");
                Glide.with(getContext()).load(training.getImage_path()).centerCrop().into(image);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        startPlan.setOnClickListener(v -> {
            user.setActiveTraining(training);
            userRef.setValue(user);
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, ProgressFragment.getInstance()).commit();

        });





    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_specific_plan, container, false);
        description = view.findViewById(R.id.plan_description);
        image = view.findViewById(R.id.plan_image);
        startPlan = view.findViewById(R.id.fab_start_plan);
        name=  view.findViewById(R.id.plan_name);
        additionalInfo = view.findViewById(R.id.plan_info);
        return view;
    }
}
