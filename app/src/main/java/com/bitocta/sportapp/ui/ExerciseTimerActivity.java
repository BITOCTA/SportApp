package com.bitocta.sportapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitocta.sportapp.R;
import com.bitocta.sportapp.UserRepo;
import com.bitocta.sportapp.db.entity.Plan;
import com.bitocta.sportapp.db.entity.User;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class ExerciseTimerActivity extends AppCompatActivity {


    TextView countDown;


    ImageView exImage;
    TextView exTitle;

    TextView exDescription;

    Button btnStart;
    Button btnEnd;
    Button btnNext;


    private DatabaseReference firebaseDB;
    private DatabaseReference userRef;

    private User user;

    CountDownTimer countDownTimer;

    int day;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_timer);


        countDown = findViewById(R.id.count_down_text);

        exImage = findViewById(R.id.ex_image);
        exTitle = findViewById(R.id.ex_title);

        exDescription = findViewById(R.id.how_to);

        btnStart = findViewById(R.id.start_exercise_btn);
        btnNext = findViewById(R.id.next_exercise_btn);
        btnEnd = findViewById(R.id.end_exercise_btn);

        day = getIntent().getExtras().getInt(ProgressFragment.POSITION_TAG);

        firebaseDB = FirebaseDatabase.getInstance().getReference();
        userRef = UserRepo.getUserRef();


        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);


                updateExercise();
                btnStart.setVisibility(View.VISIBLE);
                btnStart.setActivated(true);
                btnEnd.setVisibility(View.INVISIBLE);
                btnEnd.setActivated(false);
                btnNext.setVisibility(View.INVISIBLE);
                btnNext.setActivated(false);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (user != null) {
                    Plan plan = user.getActiveTraining().getSetsOfExercises().get(day).get(pos);

                    btnEnd.setVisibility(View.VISIBLE);
                    btnEnd.setActivated(true);
                    btnStart.setVisibility(View.INVISIBLE);
                    btnStart.setActivated(false);


                    countDown.setVisibility(View.VISIBLE);


                    countDownTimer = new CountDownTimer(plan.getSeconds() * 1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                            countDown.setText("" + millisUntilFinished / 1000);

                        }

                        @Override
                        public void onFinish() {

                        }
                    };
                    countDownTimer.start();

                }

            }
        });

        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnEnd.setVisibility(View.INVISIBLE);
                btnEnd.setActivated(false);
                btnNext.setVisibility(View.VISIBLE);
                btnNext.setActivated(true);

                countDownTimer.cancel();


            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pos++;

                updateExercise();
                btnStart.setVisibility(View.VISIBLE);
                btnStart.setActivated(true);
                btnEnd.setVisibility(View.INVISIBLE);
                btnEnd.setActivated(false);
                btnNext.setVisibility(View.INVISIBLE);
                btnNext.setActivated(false);


            }
        });


    }

    void updateExercise() {

        if (pos < user.getActiveTraining().getSetsOfExercises().get(day).size()) {


            int seconds = user.getActiveTraining().getSetsOfExercises().get(day).get(pos).getSeconds();

            if (seconds > 60) {
                countDown.setText(user.getActiveTraining().getSetsOfExercises().get(day).get(pos).getSeconds() / 60 + " minutes");
            } else {
                countDown.setText(user.getActiveTraining().getSetsOfExercises().get(day).get(pos).getSeconds() + " seconds");
            }

            Plan firstExercise = user.getActiveTraining().getSetsOfExercises().get(day).get(pos);

            Glide.with(getApplicationContext()).load(firstExercise.getExercise().getImagePath()).centerCrop().into(exImage);

            exTitle.setText(firstExercise.getExercise().getName());


            exDescription.setText(firstExercise.getExercise().getDescription());

        } else {
            user.setDay(day + 1);
            HashMap<String, Date> historyOfTrainings = user.getHistoryOfTrainings();
            if (historyOfTrainings == null) {
                historyOfTrainings = new HashMap<>();
            }
            historyOfTrainings.put(user.getActiveTraining().getName() + " " + day, new Date());
            user.setHistoryOfTrainings(historyOfTrainings);


            long totalCalories = 0;
            double totalMinutes = 0;

            for (int i = 0; i < user.getActiveTraining().getSetsOfExercises().get(day).size(); i++) {
                totalCalories += ((double) user.getActiveTraining().getSetsOfExercises().get(day).get(i).getSeconds() / 60) * 8;
                Log.d("TEST","calories " + totalCalories);
                totalMinutes += ((double) user.getActiveTraining().getSetsOfExercises().get(day).get(i).getSeconds() / 60);
                Log.d("TEST","minutes " + totalMinutes);
            }





            user.setTotalCalories(user.getTotalCalories() + totalCalories);
            user.setTotalMinutes(user.getTotalMinutes() + totalMinutes);


            userRef.setValue(user);
            Intent intent = new Intent(getApplicationContext(), EndDayActivity.class);
            Bundle mBundle = new Bundle();
            mBundle.putInt(ProgressFragment.POSITION_TAG, day);
            intent.putExtras(mBundle);
            startActivity(intent);
        }

    }
}
