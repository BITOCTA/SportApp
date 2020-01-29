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

    List<Training> trainings;

    public static final String POSITION_TAG = "position";
    public static final String PLAN_NAME_TAG = "plan_name";

    private View.OnClickListener onItemClickListener = view -> {

        RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
        int position = viewHolder.getAdapterPosition();

        Bundle bundle = new Bundle();

        bundle.putInt(POSITION_TAG, position);
        bundle.putString(PLAN_NAME_TAG, trainings.get(position).getName());

        Fragment fragment = SpecificPlanFragment.getInstance();
        fragment.setArguments(bundle);

        getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack("plans").commit();

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


        trainingsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    trainings = new ArrayList<>();
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






}
