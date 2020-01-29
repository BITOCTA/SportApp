package com.bitocta.sportapp.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.bitocta.sportapp.R;
import com.bitocta.sportapp.UserRepo;
import com.bitocta.sportapp.db.entity.User;
import com.bitocta.sportapp.util.WeightFilter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class NewWeightDialog extends AppCompatDialogFragment {


    EditText editWeight;
    private DatabaseReference userRef;
    User user;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.new_weight_dialog, null);
        editWeight = view.findViewById(R.id.edit_new_weight);

        editWeight.setText(String.valueOf(getArguments().getDouble(StatisticsFragment.CURRENT_WEIGHT_TAG)));
        editWeight.setFilters(new InputFilter[] {new WeightFilter(3,2)});


        userRef = UserRepo.getUserRef();


        builder.setView(view)
                .setTitle(getString(R.string.input_new_weight))
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        double weight = Double.valueOf(editWeight.getText().toString());
                        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                user = dataSnapshot.getValue(User.class);



                                HashMap<String, Double> historyOfWeight = user.getHistoryOfWeight();

                                if (historyOfWeight == null) {
                                    historyOfWeight = new HashMap<>();
                                    historyOfWeight.put(user.getDateOfRegistration().getTime()+"", user.getWeight());
                                }
                                historyOfWeight.put(new Date().getTime()+"", weight);
                                user.setHistoryOfWeight(historyOfWeight);

                                user.setWeight(weight);
                                userRef.setValue(user);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }
                });

        editWeight = view.findViewById(R.id.edit_new_weight);


        return builder.create();
    }
}
