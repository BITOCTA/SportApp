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
import android.widget.EditText;

import com.bitocta.sportapp.R;
import com.google.android.material.button.MaterialButton;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;


public class StartFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    private EditText editName;
    private EditText editSex;
    private EditText editHeight;
    private EditText editWeight;
    private EditText editDateOfBirth;
    private MaterialButton doneButton;


    public StartFragment() {
        // Required empty public constructor
    }


    public static StartFragment getInstance() {
        StartFragment fragment = new StartFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editDateOfBirth.setOnClickListener(mView -> {

            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    StartFragment.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)

            );



            dpd.show(getFragmentManager(), "Datepickerdialog");
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, ProgressFragment.getInstance()).commit();
            }
        });
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_start, container, false);
        editName = view.findViewById(R.id.edit_user_name);
        editSex = view.findViewById(R.id.edit_user_sex);
        editHeight = view.findViewById(R.id.edit_user_height);
        editWeight = view.findViewById(R.id.edit_user_weight);
        editDateOfBirth = view.findViewById(R.id.edit_user_date_of_birth);
        doneButton = view.findViewById(R.id.button_done);
        return view;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date;
        if (monthOfYear >= 9)
            date = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
        else
            date = dayOfMonth + ".0" + (monthOfYear + 1) + "." + year;

        editDateOfBirth.setText(date);
    }

}
