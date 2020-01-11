package com.bitocta.sportapp.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.bitocta.sportapp.R;


import com.bitocta.sportapp.UserRepo;
import com.bitocta.sportapp.db.entity.User;
import com.bitocta.sportapp.viewmodel.TrainingViewModel;
import com.bitocta.sportapp.viewmodel.UserViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class StartFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    private EditText editName;
    private EditText editSex;
    private EditText editHeight;
    private EditText editWeight;
    private EditText editDateOfBirth;
    private MaterialButton doneButton;
    private ImageView photo;

    private static final int PERMISSIONS_REQUEST_CODE = 123;
    private static final int TAKE_PHOTO_REQUEST_CODE = 1;
    private static final int CHOOSE_FROM_GALLERY_REQUEST_CODE = 2;

    String image_path;


    private DatabaseReference firebaseDB;
    private DatabaseReference userRef;

    private FirebaseAuth mAuth;


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


        firebaseDB = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        userRef = UserRepo.getUserRef();

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



        doneButton.setOnClickListener(view1 -> {

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    User user = dataSnapshot.getValue(User.class);

                    String name = editName.getText().toString();
                    double weight = Double.valueOf(editWeight.getText().toString());
                    double height = Double.valueOf(editHeight.getText().toString());

                    user.setName(name);
                    user.setWeight(weight);
                    user.setHeight(height);



                    userRef.setValue(user);

                    Intent i = new Intent(getContext(),MainActivity.class);
                    startActivity(i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY));

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



        });

        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (EasyPermissions.hasPermissions(getContext(), permissions)) {

        }else {
            EasyPermissions.requestPermissions(this, getResources().getString(R.string.permission_grant_msg), PERMISSIONS_REQUEST_CODE, permissions);
        }
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_start, container, false);
        editName = view.findViewById(R.id.edit_user_name);

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();

        }

    }


    private void selectImage() {

        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (EasyPermissions.hasPermissions(getContext(), permissions)) {

            final CharSequence[] options = {getResources().getString(R.string.take_photo_option), getResources().getString(R.string.gallery_option)};

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(getResources().getString(R.string.select_image_title));

            builder.setItems(options, (dialog, item) -> {

                if (options[item].equals(getResources().getString(R.string.take_photo_option))) {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, TAKE_PHOTO_REQUEST_CODE);

                } else if (options[item].equals(getResources().getString(R.string.gallery_option))) {
                    Intent pickPhoto = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, CHOOSE_FROM_GALLERY_REQUEST_CODE);

                }
            });
            builder.show();
        } else {
            EasyPermissions.requestPermissions(this, getResources().getString(R.string.permission_grant_msg), PERMISSIONS_REQUEST_CODE, permissions);
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case TAKE_PHOTO_REQUEST_CODE:
                    if (resultCode == RESULT_OK && data != null) {

                        Bitmap taken_photo = (Bitmap) data.getExtras().get("data");

                        Glide.with(getContext()).load(photo).apply(RequestOptions.circleCropTransform()).into(photo);

                        image_path = getRealPathFromURI(getImageUri(getContext(), taken_photo), getContext());

                    }
                    break;
                case CHOOSE_FROM_GALLERY_REQUEST_CODE:
                    if (resultCode == RESULT_OK && data != null) {
                        Glide.with(getContext()).load(data.getData()).apply(RequestOptions.circleCropTransform()).into(photo);
                        image_path = data.getDataString();
                    }

                    break;

            }
        }
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {

        }
    }



    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static String getRealPathFromURI(Uri uri, Context context) {

        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);

    }

}
