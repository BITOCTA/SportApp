package com.bitocta.sportapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bitocta.sportapp.R;
import com.bitocta.sportapp.db.entity.Training;

import java.util.ArrayList;
import java.util.List;

public class PlansFragment extends Fragment {

    public static Context context;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private PlansListAdapter plansListAdapter;

    public static PlansFragment getInstance() {
        PlansFragment fragment = new PlansFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context=getContext();

        layoutManager = new LinearLayoutManager(context);

        plansListAdapter = new PlansListAdapter();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(plansListAdapter);


        String downloadFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        List<Training> trainingsList = new ArrayList<>();


        trainingsList.add(new Training("Start lifting",null, downloadFolder+"/pic7.jpg",null,null));
        trainingsList.add(new Training("Get muscles",null,downloadFolder+"/pic4.jpg",null,null));
        trainingsList.add(new Training("Get ready for summer",null,downloadFolder+"/pic5.jpg",null,null));
        trainingsList.add(new Training("Advanced lifting",null,downloadFolder+"/pic3.jpg",null,null));
        trainingsList.add(new Training("Find your balance",null,downloadFolder+"/pic6.jpg",null,null));
        trainingsList.add(new Training("Get your triceps",null,downloadFolder+"/pic1.jpg",null,null));

        plansListAdapter.updateTrainingListItems(trainingsList);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plans, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_plans);




        return view;
    }


}
