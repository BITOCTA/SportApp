package com.bitocta.sportapp.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bitocta.sportapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if( savedInstanceState == null ) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, StartFragment.getInstance()).commit();
        }
    }
}
