package com.bitocta.sportapp.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bitocta.sportapp.R;
import com.bitocta.sportapp.UserRepo;
import com.bitocta.sportapp.chart.WeightMarkerView;
import com.bitocta.sportapp.db.entity.User;
import com.bitocta.sportapp.chart.DateFormatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class StatisticsFragment extends Fragment {

    private DatabaseReference firebaseDB;
    private DatabaseReference userRef;

    TextView totalTrainings;
    TextView totalCalories;
    TextView totalTime;
    TextView currentWeight;
    TextView updateWeightText;
    TextView dateOfWeightUpdatedText;
    TextView day1Text, day2Text, day3Text, day4Text, day5Text, day6Text, day7Text;
    ImageView day1Img, day2Img, day3Img, day4Img, day5Img, day6Img, day7Img;

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

                SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                Calendar calendar = Calendar.getInstance();

                calendar.set(Calendar.DAY_OF_WEEK, Calendar.getInstance().getFirstDayOfWeek());
                String[] days = new String[7];
                int[] daysDone = new int[7];
                ArrayList<Date> trainingDates = new ArrayList<>(user.getHistoryOfTrainings().values());
                ArrayList<String> trainingDatesString = new ArrayList<>();
                for(Date date:trainingDates){
                    trainingDatesString.add(sdf.format(date));
                }


                for (int i = 0; i < 7; i++) {
                    if(trainingDatesString.contains(sdf.format(calendar.getTime()))){
                        daysDone[i]=1;
                    }
                    days[i] = dayFormat.format(calendar.getTime());
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                }



                day1Text.setText(days[0]);
                day2Text.setText(days[1]);
                day3Text.setText(days[2]);
                day4Text.setText(days[3]);
                day5Text.setText(days[4]);
                day6Text.setText(days[5]);
                day7Text.setText(days[6]);
                if(daysDone[0]==1){
                    day1Img.setImageDrawable(getContext().getDrawable(R.drawable.day_done));
                }
                if(daysDone[1]==1){
                    day2Img.setImageDrawable(getContext().getDrawable(R.drawable.day_done));
                }
                if(daysDone[2]==1){
                    day3Img.setImageDrawable(getContext().getDrawable(R.drawable.day_done));
                }
                if(daysDone[3]==1){
                    day4Img.setImageDrawable(getContext().getDrawable(R.drawable.day_done));
                }
                if(daysDone[4]==1){
                    day5Img.setImageDrawable(getContext().getDrawable(R.drawable.day_done));
                }
                if(daysDone[5]==1){
                    day6Img.setImageDrawable(getContext().getDrawable(R.drawable.day_done));
                }
                if(daysDone[6]==1){
                    day7Img.setImageDrawable(getContext().getDrawable(R.drawable.day_done));
                }








                HashMap<String, Double> historyOfWeight = user.getHistoryOfWeight();

                if (historyOfWeight.size() > 1) {




                    ArrayList<String> weightDates = new ArrayList<>(historyOfWeight.keySet());
                    ArrayList<Double> weights = new ArrayList<>(historyOfWeight.values());

                    List<Entry> entries = new ArrayList<Entry>();
                    ArrayList<Pair<Date, Double>> listOfDatesAndWeights = new ArrayList<Pair<Date, Double>>();

                    for (int i = 0; i < historyOfWeight.size(); i++) {
                        listOfDatesAndWeights.add(new Pair<>(new Date(Long.valueOf(weightDates.get(i))), weights.get(i)));
                    }
                    Collections.sort(listOfDatesAndWeights, new Comparator<Pair<Date, Double>>() {
                        @Override
                        public int compare(Pair<Date, Double> o1, Pair<Date, Double> o2) {
                            return o1.first.compareTo(o2.first);
                        }
                    });

                    dateOfWeightUpdatedText.setText(sdf.format(listOfDatesAndWeights.get(listOfDatesAndWeights.size() - 1).first));

                    long referenceTime = listOfDatesAndWeights.get(0).first.getTime();

                    for (Pair<Date, Double> pair : listOfDatesAndWeights) {
                        entries.add(new Entry(pair.first.getTime()-referenceTime, pair.second.floatValue()));
                        Log.d("TEST", pair.first.getTime()+"");
                    }


                    ValueFormatter xAxisFormatter = new DateFormatter(referenceTime);
                    XAxis xAxis = weightChart.getXAxis();
                    xAxis.setValueFormatter(xAxisFormatter);
                    LineDataSet dataSet = new LineDataSet(entries, null);
                    dataSet.setDrawFilled(true);
                    dataSet.setFillDrawable(getContext().getDrawable(R.drawable.gradient_fill));
                    dataSet.setColor(getContext().getColor(R.color.colorPrimaryDark));
                    dataSet.setLineWidth(2);
                    dataSet.setCircleColor(getContext().getColor(R.color.colorPrimaryLight));


                    LineData lineData = new LineData(dataSet);

                    WeightMarkerView mv = new WeightMarkerView(getContext(), R.layout.weight_marker_view,referenceTime);
                    weightChart.setMarkerView(mv);

                    weightChart.getAxisLeft().setDrawGridLines(false);
                    weightChart.getXAxis().setDrawGridLines(false);
                    weightChart.getXAxis().setEnabled(false);
                    weightChart.getAxisLeft().setDrawLabels(false);
                    weightChart.getAxisRight().setDrawLabels(false);
                    weightChart.getAxisLeft().setDrawAxisLine(false);
                    weightChart.getAxisRight().setDrawAxisLine(false);
                    weightChart.getAxisRight().setDrawGridLines(false);
                    weightChart.getAxisLeft().setDrawGridLines(false);
                    weightChart.getXAxis().setDrawGridLines(false);
                    weightChart.setData(lineData);
                    weightChart.getDescription().setEnabled(false);
                    weightChart.getLegend().setEnabled(false);
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
        dateOfWeightUpdatedText = view.findViewById(R.id.date_of_weight_updated_text);
        weightChart = view.findViewById(R.id.weight_chart);

        day1Text = view.findViewById(R.id.day1_text);
        day2Text = view.findViewById(R.id.day2_text);
        day3Text = view.findViewById(R.id.day3_text);
        day4Text = view.findViewById(R.id.day4_text);
        day5Text = view.findViewById(R.id.day5_text);
        day6Text = view.findViewById(R.id.day6_text);
        day7Text = view.findViewById(R.id.day7_text);

        day1Img = view.findViewById(R.id.day1_image);
        day2Img = view.findViewById(R.id.day2_image);
        day3Img = view.findViewById(R.id.day3_image);
        day4Img = view.findViewById(R.id.day4_image);
        day5Img = view.findViewById(R.id.day5_image);
        day6Img = view.findViewById(R.id.day6_image);
        day7Img = view.findViewById(R.id.day7_image);


        return view;
    }

    public void openDialog() {
        NewWeightDialog newWeightDialog = new NewWeightDialog();
        newWeightDialog.show(getFragmentManager(), NEW_WEIGHT_DIALOG_TAG);
    }
}
