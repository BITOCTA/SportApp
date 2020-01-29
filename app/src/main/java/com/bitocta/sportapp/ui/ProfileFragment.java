package com.bitocta.sportapp.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitocta.sportapp.R;
import com.bitocta.sportapp.UserRepo;
import com.bitocta.sportapp.db.entity.User;
import com.bitocta.sportapp.util.AvatarUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.bitocta.sportapp.ui.StartFragment.getImageUri;


public class ProfileFragment extends Fragment {

    private ImageView avatar;
    private TextView editProfileText;
    private ImageView achievementsButton;


    private DatabaseReference firebaseDB;
    private DatabaseReference userRef;

    private User user;


    private static final int PERMISSIONS_REQUEST_CODE = 123;
    private static final int TAKE_PHOTO_REQUEST_CODE = 1;
    private static final int CHOOSE_FROM_GALLERY_REQUEST_CODE = 2;

    private String image_path;


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
        userRef = UserRepo.getUserRef();

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
        /*
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });*/







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
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, CHOOSE_FROM_GALLERY_REQUEST_CODE);

                }
            });
            builder.show();
        } else {
            EasyPermissions.requestPermissions(this, getResources().getString(R.string.permission_grant_msg), PERMISSIONS_REQUEST_CODE, permissions);
        }

    }
/*
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case TAKE_PHOTO_REQUEST_CODE:
                    if (resultCode == RESULT_OK && data != null) {

                        Bitmap photo = (Bitmap) data.getExtras().get("data");

                        Glide.with(getContext()).load(photo).apply(RequestOptions.circleCropTransform()).into(avatar);

                        image_path = AvatarUtils.getRealPathFromURI(getImageUri(getContext(), photo), getContext());
                        uploadImgToFirestore(image_path);

                    }
                    break;
                case CHOOSE_FROM_GALLERY_REQUEST_CODE:
                    if (resultCode == RESULT_OK && data != null) {
                        Glide.with(getContext()).load(data.getData()).apply(RequestOptions.circleCropTransform()).into(avatar);
                        image_path = data.getDataString();
                        uploadImgToFirestore(image_path);
                    }

                    break;

            }
        }
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {

        }
    }

    private void uploadImgToFirestore(String pathname){

        Log.d("test","we are her");
        Uri file = Uri.fromFile(new File(pathname));
        StorageReference avatarsRef = FirebaseStorage.getInstance().getReference().child("avatars");


        avatarsRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                        user.setImage_path(downloadUrl.getPath());
                        userRef.setValue(user);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }*/

}
