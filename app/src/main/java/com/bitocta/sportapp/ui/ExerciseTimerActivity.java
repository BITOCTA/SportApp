package com.bitocta.sportapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitocta.sportapp.R;
import com.bitocta.sportapp.db.entity.Exercise;
import com.bitocta.sportapp.db.entity.Plan;
import com.bitocta.sportapp.db.entity.User;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ExerciseTimerActivity extends AppCompatActivity {

    ImageView arrow;
    Animation rotateAnimation;
    TextView countDown;
    Chronometer chronometer;

    ImageView exImage;
    TextView exTitle;
    TextView exReps;
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

        arrow = findViewById(R.id.arrow);
        countDown = findViewById(R.id.count_down_text);
        chronometer = findViewById(R.id.chronometer);
        exImage = findViewById(R.id.ex_image);
        exTitle = findViewById(R.id.ex_title);
        exReps = findViewById(R.id.ex_reps);
        exDescription = findViewById(R.id.how_to);

        btnStart = findViewById(R.id.start_exercise_btn);
        btnNext = findViewById(R.id.next_exercise_btn);
        btnEnd = findViewById(R.id.end_exercise_btn);

        day = getIntent().getExtras().getInt(ProgressFragment.POSITION_TAG);

        firebaseDB = FirebaseDatabase.getInstance().getReference();
        userRef = firebaseDB.child("user");

        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotatearrow);


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

                    if (plan.getReps() == 0) {
                        chronometer.setVisibility(View.INVISIBLE);
                        countDown.setVisibility(View.VISIBLE);
                        rotateAnimation.setDuration(plan.getMinutes() * 60000);
                        arrow.setAnimation(rotateAnimation);

                        countDownTimer = new CountDownTimer(plan.getMinutes() * 60000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {

                                countDown.setText("" + millisUntilFinished / 1000);

                            }

                            @Override
                            public void onFinish() {

                            }
                        };
                    } else {
                        chronometer.setVisibility(View.VISIBLE);
                        countDown.setVisibility(View.INVISIBLE);

                        chronometer.start();
                    }
                }
                arrow.startAnimation(rotateAnimation);
            }
        });

        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnEnd.setVisibility(View.INVISIBLE);
                btnEnd.setActivated(false);
                btnNext.setVisibility(View.VISIBLE);
                btnNext.setActivated(true);

                if (chronometer != null) {
                    chronometer.stop();
                }

                if (countDownTimer != null) {
                    countDownTimer.onFinish();
                }


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


            Plan firstExercise = user.getActiveTraining().getSetsOfExercises().get(day).get(pos);

            Glide.with(getApplicationContext()).load(firstExercise.getExercise().getImagePath()).centerCrop().into(exImage);

            exTitle.setText(firstExercise.getExercise().getName());

            if (firstExercise.getReps() == 0) {
                exReps.setText(firstExercise.getMinutes() + " " + getResources().getString(R.string.minutes));
            } else {
                exReps.setText(firstExercise.getReps() + " " + getResources().getString(R.string.reps));
            }

            exDescription.setText(firstExercise.getExercise().getDescription());

        } else {
            user.setDay(day + 1);
            userRef.setValue(user);
            Intent intent = new Intent(getApplicationContext(), EndDayActivity.class);
            Bundle mBundle = new Bundle();
            mBundle.putInt(ProgressFragment.POSITION_TAG, day);
            intent.putExtras(mBundle);
            startActivity(intent);
        }

    }
}
