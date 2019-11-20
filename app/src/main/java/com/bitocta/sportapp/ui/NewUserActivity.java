package com.bitocta.sportapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.bitocta.sportapp.R;
import com.bitocta.sportapp.viewmodel.UserViewModel;

public class NewUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        userViewModel.getAllUsers().observe(this, users -> {
            if (users.size() == 0) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container_new_user, StartFragment.getInstance()).commit();
            }
            else {
                Intent i = new Intent(this,MainActivity.class);
                startActivity(i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY));
            }
        });
    }
}
