package com.bitocta.sportapp.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitocta.sportapp.R;
import com.bitocta.sportapp.db.entity.User;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileFragment extends Fragment {

    private ImageView avatar;
    private TextView editProfileText;
    private ImageView achievementsButton;


    private DatabaseReference firebaseDB;
    private DatabaseReference userRef;

    private User user;


    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment getInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity.toolbar.setTitle(getString(R.string.profile));

        firebaseDB = FirebaseDatabase.getInstance().getReference();
        userRef = firebaseDB.child("user");

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                if(user.getImage_path()!=null){
                    Glide.with(getContext()).load(user.getImage_path()).apply(RequestOptions.circleCropTransform()).into(avatar);
                }
                else{
                    Glide.with(getContext()).load(getResources().getDrawable(R.drawable.unknown512)).apply(RequestOptions.circleCropTransform()).into(avatar);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        avatar = view.findViewById(R.id.profile_avatar);
        editProfileText = view.findViewById(R.id.edit_profile_text);
        achievementsButton = view.findViewById(R.id.achievements_btn);

        return view;


    }
}
