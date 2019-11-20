package com.bitocta.sportapp.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;

import com.bitocta.sportapp.R;
import com.bitocta.sportapp.db.entity.User;
import com.bitocta.sportapp.viewmodel.UserViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    static User mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation);
        toolbar = findViewById(R.id.toolbar);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        View headerView = navigationView.getHeaderView(0);


        TextView name = headerView.findViewById(R.id.nav_user_name);
        ImageView image = headerView.findViewById(R.id.nav_user_image);


        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        userViewModel.getAllUsers().observe(this, users -> {

            if (users.size() != 0) {
                User user = users.get(0);
                name.setText(user.getName());
                if (user.getImage_path() != null) {
                    Glide.with(this).load(user.getImage_path()).apply(RequestOptions.circleCropTransform()).into(image);
                } else {
                    Glide.with(this).load(getDrawable(R.drawable.unknown512)).apply(RequestOptions.circleCropTransform()).into(image);
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, ProgressFragment.getInstance()).commit();
            } else {
                if (savedInstanceState == null) {


                }

            }

        });


    }

    public static void setUser(User user) {
        mUser = user;

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.progress: {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, ProgressFragment.getInstance()).commit();
                break;
            }
            case R.id.plans: {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, PlansFragment.getInstance()).commit();
                break;
            }
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
