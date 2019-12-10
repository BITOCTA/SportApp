package com.bitocta.sportapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bitocta.sportapp.R;
import com.bitocta.sportapp.db.entity.Exercise;
import com.bitocta.sportapp.db.entity.Plan;
import com.bitocta.sportapp.db.entity.Training;
import com.bitocta.sportapp.viewmodel.PlanViewModel;
import com.bitocta.sportapp.viewmodel.TrainingViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.bitocta.sportapp.ui.MainActivity.toolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PlansFragment extends Fragment {

    public static Context context;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private PlansListAdapter plansListAdapter;

    private DatabaseReference firebaseDB;
    private DatabaseReference trainingsRef;
    private DatabaseReference plansRef;

    public static final String POSITION_TAG = "position";

    private View.OnClickListener onItemClickListener = view -> {

        RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
        int position = viewHolder.getAdapterPosition();

        Bundle bundle = new Bundle();

        bundle.putInt(POSITION_TAG, position);

        Fragment fragment = SpecificPlanFragment.getInstance();
        fragment.setArguments(bundle);

        getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();

    };


    public static PlansFragment getInstance() {
        PlansFragment fragment = new PlansFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context=getContext();

        toolbar.setTitle(getResources().getString(R.string.plans));

        firebaseDB = FirebaseDatabase.getInstance().getReference();
        trainingsRef = firebaseDB.child("trainings");
        plansRef = firebaseDB.child("plans");

        layoutManager = new LinearLayoutManager(context);

        plansListAdapter = new PlansListAdapter();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(plansListAdapter);




        for(Plan plan : getPlans())
        {
            plansRef.child(plan.getPid()+"").setValue(plan);
        }
        for(Training training : getTrainings()){
            trainingsRef.child(training.getTid()+"").setValue(training);
        }

        trainingsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    List<Training> trainings = new ArrayList<>();
                    for(int i=0; i<dataSnapshot.getChildrenCount(); i++){
                        trainings.add(dataSnapshot.getChildren().iterator().next().getValue(Training.class));

                    }
                    plansListAdapter.updateTrainingListItems(trainings);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        plansListAdapter.setOnItemClickListener(onItemClickListener);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plans, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_plans);




        return view;
    }


    
    static ArrayList<Plan> getPlans(){
        ArrayList<Plan> plans = new ArrayList<>();
        ArrayList<Exercise.ExerciseMuscles> pushUpsMuscles  = new ArrayList<>();
        pushUpsMuscles.add(Exercise.ExerciseMuscles.CHEST);
        pushUpsMuscles.add(Exercise.ExerciseMuscles.BICEPS);
        pushUpsMuscles.add(Exercise.ExerciseMuscles.FOREARMS);
        ArrayList<Exercise.ExerciseMuscles> squatsMuscles = new ArrayList<>();
        squatsMuscles.add(Exercise.ExerciseMuscles.LOWER_BACK);
        squatsMuscles.add(Exercise.ExerciseMuscles.LEGS);
        ArrayList<Exercise.ExerciseMuscles> runningMuscles = new ArrayList<>();
        runningMuscles.add(Exercise.ExerciseMuscles.LEGS);
        plans.add(new Plan("push-ups_easy",new Exercise("Push-ups","https://images.unsplash.com/photo-1509010060764-b503676a3048?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjF9", Exercise.ExerciseType.STRENGTH, Exercise.ExerciseLevel.BEGINNER,pushUpsMuscles, Exercise.ExerciseEquipment.BODY_ONLY),3,10,0,30));
        plans.add(new Plan("squats_easy",new Exercise("Squats","https://images.unsplash.com/flagged/photo-1566064336477-864e4f308992?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjF9", Exercise.ExerciseType.STRENGTH, Exercise.ExerciseLevel.BEGINNER,squatsMuscles, Exercise.ExerciseEquipment.BODY_ONLY),3,10,0,30));
        plans.add(new Plan("running_easy", new Exercise("Running","https://images.unsplash.com/photo-1566351557863-467d204a9f8a?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjF9", Exercise.ExerciseType.CARDIO, Exercise.ExerciseLevel.BEGINNER,runningMuscles, Exercise.ExerciseEquipment.BODY_ONLY),1,0,10,60));
        plans.add(new Plan("pull-ups_easy", new Exercise("Pull-ups", "https://images.unsplash.com/photo-1526506118085-60ce8714f8c5?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjF9", Exercise.ExerciseType.STRENGTH, Exercise.ExerciseLevel.BEGINNER,pushUpsMuscles, Exercise.ExerciseEquipment.BODY_ONLY),2,5,0,30));
        plans.add(new Plan("reverse-curl-easy",new Exercise("Reverse Curl","https://images.unsplash.com/photo-1563053764-149651f44800?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjF9", Exercise.ExerciseType.WEIGHTLIFTING, Exercise.ExerciseLevel.BEGINNER,pushUpsMuscles, Exercise.ExerciseEquipment.DUMBBELL),3,10,0,30 ));
        plans.add(new Plan("dips-easy",new Exercise("Dips","https://images.unsplash.com/photo-1526407297627-d845b359a55b?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjF9", Exercise.ExerciseType.STRENGTH, Exercise.ExerciseLevel.BEGINNER,pushUpsMuscles, Exercise.ExerciseEquipment.MACHINE),3,5,0,30 ));
        plans.add(new Plan("barbell-curl-easy",new Exercise("Barbell Curl","https://images.unsplash.com/photo-1526405294019-7f3f7c8c7867?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjF9", Exercise.ExerciseType.WEIGHTLIFTING, Exercise.ExerciseLevel.BEGINNER,pushUpsMuscles, Exercise.ExerciseEquipment.BARBELL),3,5,0,30));
        return plans;
    }
    static  ArrayList<Training> getTrainings(){
        ArrayList<Training> trainings = new ArrayList<>();
        ArrayList<ArrayList<Plan>> setsOfExercise = new ArrayList<>();
        ArrayList<Plan> plans = getPlans();

        String downloadFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();


        for(int i = 0; i<30; i++) {
            ArrayList<Plan> day = new ArrayList<>();


            for(int j=0; j<3; j++) {
                Random rnd = new Random();
                int randomExercise = rnd.nextInt(plans.size() - 1);
                day.add(plans.get(randomExercise));
            }
            setsOfExercise.add(day);
        }
        trainings.add(new Training("Find your balance","In the command style of coaching, the coach makes all the decisions. The role of the athlete is to respond to the coach’s commands. The assumption underlying this approach is that because the coach has knowledge and experience, it is his or her role to tell the athlete what to do. The athlete’s role is to listen, to absorb, and to comply.",downloadFolder+"/pic6.jpg",setsOfExercise));

        return trainings;
    }

}
