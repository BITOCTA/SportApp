package com.bitocta.sportapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.bitocta.sportapp.R;
import com.google.android.material.navigation.NavigationView;


public class ProgressFragment extends Fragment {

    private DrawerLayout mDrawerLayout;
    private NavigationView nv;
    private Toolbar toolbar;


    public ProgressFragment() {
        // Required empty public constructor
    }


    public static ProgressFragment getInstance() {
        ProgressFragment fragment = new ProgressFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_progress, container, false);


        mDrawerLayout = view.findViewById(R.id.drawer_layout);
        nv = view.findViewById(R.id.navigation);
        toolbar = view.findViewById(R.id.z_toolbar);

        return view;
    }


}
