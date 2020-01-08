package com.bitocta.sportapp.ui;

import android.os.Bundle;
import android.util.Log;
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
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.amitshekhar.DebugDB;
import com.bitocta.sportapp.Firebase;
import com.bitocta.sportapp.R;
import com.bitocta.sportapp.UserRepo;
import com.bitocta.sportapp.db.FirebaseDB;
import com.bitocta.sportapp.db.entity.User;
import com.bitocta.sportapp.viewmodel.UserViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout mDrawerLayout;
    public static NavigationView navigationView;
    public static Toolbar toolbar;
    static User mUser;

    private DatabaseReference firebaseDB;
    private DatabaseReference userRef;

    private View.OnClickListener profileClick;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseDB = FirebaseDatabase.getInstance().getReference();
        userRef = UserRepo.getUserRef();

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
        TextView currentProgram  = headerView.findViewById(R.id.nav_current_program);
        ImageView image = headerView.findViewById(R.id.nav_user_image);

        profileClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, ProfileFragment.getInstance()).commit();
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        };

        image.setOnClickListener(profileClick);
        name.setOnClickListener(profileClick);





        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChild("name")) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, StartFragment.getInstance()).commit();

                } else {
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.getValue(User.class);
                            name.setText(user.getName());
                            Glide.with(getApplicationContext()).load(getDrawable(R.drawable.unknown512)).apply(RequestOptions.circleCropTransform()).into(image);
                            if(user.getActiveTraining()!=null){
                                currentProgram.setText(currentProgram.getText()+" "+ user.getActiveTraining().getName());
                            }
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, ProgressFragment.getInstance()).commitAllowingStateLoss();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
            case R.id.statistics:{
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,StatisticsFragment.getInstance()).commit();
                break;
            }
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



}
