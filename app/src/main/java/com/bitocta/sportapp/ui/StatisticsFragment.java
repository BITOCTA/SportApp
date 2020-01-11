package com.bitocta.sportapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
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
import com.bitocta.sportapp.formatter.DateFormatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class StatisticsFragment  extends Fragment {

    private DatabaseReference firebaseDB;
    private DatabaseReference userRef;

    TextView totalTrainings;
    TextView totalCalories;
    TextView totalTime;
    TextView currentWeight;
    TextView updateWeightText;
    LineChart weightChart;

    User user;

    private final String NEW_WEIGHT_DIALOG_TAG = "new weight dialog";

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

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                totalTrainings.setText(String.valueOf(user.getHistoryOfTrainings().size()));
                totalCalories.setText(user.getTotalCalories() + " " + getText(R.string.kcal));
                totalTime.setText((int) user.getTotalMinutes() / 60 + "h" + (int) user.getTotalMinutes() % 60 + "m");

                currentWeight.setText(user.getWeight() + " " + getText(R.string.kg));

                ArrayList<Date> dates = new ArrayList<>(user.getHistoryOfTrainings().values());
                Collections.sort(dates);
                Date lastDate = dates.get(dates.size() - 1);


                HashMap<String, Double> historyOfWeight = user.getHistoryOfWeight();

                if (historyOfWeight.size() > 1) {

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


                    ArrayList<String> weightDates = new ArrayList<>(historyOfWeight.keySet());
                    ArrayList<Double> weights = new ArrayList<>(historyOfWeight.values());

                    List<Entry> entries = new ArrayList<Entry>();
                    ArrayList<Pair<Date,Double>> listOfDatesAndWeights =new ArrayList<Pair<Date, Double>>();

                    for (int i = 0; i < historyOfWeight.size(); i++) {
                        try {
                            listOfDatesAndWeights.add(new Pair<>(sdf.parse(weightDates.get(i)),weights.get(i)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    Collections.sort(listOfDatesAndWeights, new Comparator<Pair<Date, Double>>() {
                        @Override
                        public int compare(Pair<Date, Double> o1, Pair<Date, Double> o2) {
                            return o1.first.compareTo(o2.first);
                        }
                    });
                   for(Pair<Date,Double> pair : listOfDatesAndWeights){
                       entries.add(new Entry(pair.first.getTime()-listOfDatesAndWeights.get(0).first.getTime(),pair.second.floatValue()));
                   }

                    ValueFormatter xAxisFormatter = new DateFormatter(listOfDatesAndWeights.get(0).first.getTime());
                    XAxis xAxis = weightChart.getXAxis();
                    xAxis.setValueFormatter(xAxisFormatter);
                    LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
                    LineData lineData = new LineData(dataSet);

                    weightChart.setData(lineData);
                    weightChart.invalidate();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        updateWeightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
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
        updateWeightText = view.findViewById(R.id.update_weight_text);
        weightChart = view.findViewById(R.id.weight_chart);


        return view;
    }

    public void openDialog() {
        NewWeightDialog newWeightDialog = new NewWeightDialog();
        newWeightDialog.show(getFragmentManager(), NEW_WEIGHT_DIALOG_TAG);
    }
}
