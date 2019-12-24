package com.bitocta.sportapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitocta.sportapp.R;

public class EndDayActivity extends AppCompatActivity {

    private TextView dayText;
    private TextView congratulationsText;
    private TextView youMadeItText;
    private int day;
    private Animation bottom_apr;
    private Animation simple_alpha;
    private ImageView trophyImage;

    private Button goBackButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_day);

        congratulationsText = findViewById(R.id.congratulations);
        youMadeItText = findViewById(R.id.youmadeit);
        goBackButton = findViewById(R.id.go_to_progress_btn);

        dayText = findViewById(R.id.day_trophey);
        trophyImage = findViewById(R.id.trophy);


        day = getIntent().getExtras().getInt(ProgressFragment.POSITION_TAG);

        bottom_apr = AnimationUtils.loadAnimation(this, R.anim.appear_from_bottom);
        simple_alpha = AnimationUtils.loadAnimation(this, R.anim.simple_alpha);

        congratulationsText.startAnimation(simple_alpha);
        youMadeItText.startAnimation(simple_alpha);
        goBackButton.startAnimation(simple_alpha);

        trophyImage.startAnimation(bottom_apr);
        dayText.startAnimation(bottom_apr);

        dayText.setText("Day " + (day + 1));

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });


    }
}
