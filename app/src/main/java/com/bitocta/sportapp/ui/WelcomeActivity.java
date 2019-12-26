package com.bitocta.sportapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.util.StringUtil;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitocta.sportapp.R;
import com.bitocta.sportapp.db.FirebaseDB;
import com.bitocta.sportapp.db.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.apache.commons.lang3.StringUtils;

public class WelcomeActivity extends AppCompatActivity {

    ImageButton startButton;
    ImageView logo;
    ImageView outlinedLogo;
    TextView name;
    TextView description;
    TextView startText;
    TextInputLayout emailInput;
    TextInputLayout passwordInput;
    EditText emailEdit;
    EditText passwordEdit;
    ImageButton loginButton;
    TextView loginText;
    View divider;
    TextView dontHaveAnAccountText;
    TextView signUpText;


    private FirebaseAuth mAuth;
    private DatabaseReference firebaseDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();


        firebaseDB = FirebaseDB.getDatabase().getReference();


        startButton = findViewById(R.id.start_button);
        logo = findViewById(R.id.app_logo_img);
        outlinedLogo = findViewById(R.id.logo_outlined);
        name = findViewById(R.id.app_name);
        startText = findViewById(R.id.start_text);
        description = findViewById(R.id.app_descr);
        emailInput = findViewById(R.id.input_email);
        passwordInput = findViewById(R.id.input_pass);
        emailEdit = findViewById(R.id.edit_email);
        passwordEdit = findViewById(R.id.edit_pass);
        loginButton = findViewById(R.id.login_button);
        loginText = findViewById(R.id.login_text);
        divider = findViewById(R.id.login_divider);
        dontHaveAnAccountText = findViewById(R.id.dont_have_account_text);
        signUpText = findViewById(R.id.sign_up_text);


        outlinedLogo.setVisibility(View.INVISIBLE);
        emailInput.setVisibility(View.INVISIBLE);
        passwordInput.setVisibility(View.INVISIBLE);
        loginButton.setVisibility(View.INVISIBLE);
        loginText.setVisibility(View.INVISIBLE);
        divider.setVisibility(View.INVISIBLE);
        dontHaveAnAccountText.setVisibility(View.INVISIBLE);
        signUpText.setVisibility(View.INVISIBLE);
        signUpText.setActivated(false);
        emailInput.setActivated(false);
        passwordInput.setActivated(false);
        loginButton.setActivated(false);


        Animation simple_alpha_app = AnimationUtils.loadAnimation(this, R.anim.simple_alpha_app);
        simple_alpha_app.setDuration(300);
        Animation simple_alpha_dis = AnimationUtils.loadAnimation(this, R.anim.simple_alpha_dis);
        simple_alpha_dis.setDuration(300);
        Animation translate_dis = AnimationUtils.loadAnimation(this, R.anim.translate_diss);
        translate_dis.setDuration(300);


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // slideView(logo,logo.getLayoutParams().height,300);

                logo.startAnimation(translate_dis);
                logo.setVisibility(View.INVISIBLE);
                outlinedLogo.startAnimation(simple_alpha_app);
                outlinedLogo.setVisibility(View.VISIBLE);

                emailInput.startAnimation(simple_alpha_app);
                emailInput.setVisibility(View.VISIBLE);
                passwordInput.startAnimation(simple_alpha_app);
                passwordInput.setVisibility(View.VISIBLE);
                name.startAnimation(translate_dis);
                name.setVisibility(View.INVISIBLE);
                description.startAnimation(translate_dis);
                description.setVisibility(View.INVISIBLE);

                loginButton.startAnimation(simple_alpha_app);
                loginButton.setVisibility(View.VISIBLE);
                loginButton.setActivated(true);

                loginText.startAnimation(simple_alpha_app);
                loginText.setVisibility(View.VISIBLE);

                startButton.startAnimation(translate_dis);
                startButton.setVisibility(View.INVISIBLE);

                divider.startAnimation(simple_alpha_app);
                divider.setVisibility(View.VISIBLE);
                dontHaveAnAccountText.startAnimation(simple_alpha_app);
                dontHaveAnAccountText.setVisibility(View.VISIBLE);
                signUpText.startAnimation(simple_alpha_app);
                signUpText.setVisibility(View.VISIBLE);

                startText.startAnimation(translate_dis);
                startText.setVisibility(View.INVISIBLE);


                signUpText.setActivated(true);
                emailInput.setActivated(true);
                passwordInput.setActivated(true);
                startButton.setActivated(false);


            }
        });

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailInput.getEditText().getText().toString();
                String password = passwordInput.getEditText().getText().toString();

                if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "You should input both email and password", Toast.LENGTH_LONG).show();
                } else {

                    Log.d("TEST","email:"+email+" password:"+password);

                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(WelcomeActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                FirebaseUser user = mAuth.getCurrentUser();
                                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(), "Registered successfully. Check your e-mail for verification", Toast.LENGTH_LONG).show();
                                        }

                                    }
                                });


                            } else {

                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    /*
                    firebaseDB.child("users").child(email.replace(".", "")).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {

                                Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_LONG).show();
                                // use "username" already exists
                                // Let the user know he needs to pick another username.
                            } else {
                                // User does not exist. NOW call createUserWithEmailAndPassword

                                mAuth.createUserWithEmailAndPassword(emailEdit.getText().toString(), passwordEdit.getText().toString()).addOnCompleteListener();
                                User user = new User(email);
                                firebaseDB.child("users").child(email.replace(".", "")).setValue(user);
                                // Your previous code here.

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });*/

                }
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "You should input both email and password", Toast.LENGTH_LONG).show();

                } else {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(WelcomeActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                if(mAuth.getCurrentUser().isEmailVerified()) {
                                    //mAuth.getCurrentUser().getEmail().replace(".","");
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"Please, verify your e-mail adress",Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });


    }

    public static void slideView(View view,
                                 int currentHeight,
                                 int newHeight) {

        ValueAnimator slideAnimator = ValueAnimator
                .ofInt(currentHeight, newHeight)
                .setDuration(500);

        /* We use an update listener which listens to each tick
         * and manually updates the height of the view  */

        slideAnimator.addUpdateListener(animation1 -> {
            Integer value = (Integer) animation1.getAnimatedValue();
            view.getLayoutParams().height = value.intValue();
            view.requestLayout();
        });

        /*  We use an animationSet to play the animation  */

        AnimatorSet animationSet = new AnimatorSet();
        animationSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animationSet.play(slideAnimator);
        animationSet.start();
    }
}
