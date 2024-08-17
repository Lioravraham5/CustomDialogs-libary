package com.example.customdialogsproject;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.customdialogs.ProfileImagePickerDialog.ProfileImagePickerFragmentBuilder;
import com.example.customdialogs.RatingDialog.RatingAppDialogBuilder;
import com.example.customdialogs.RatingDialog.RatingAppDialogCallback;
import com.example.customdialogs.RatingDialog.RatingAppOnStoreDialogCallback;
import com.example.customdialogs.RegisterDialog.OnRegistrationCompleteListener;
import com.example.customdialogs.RegisterDialog.RegistrationDialogBuilder;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Test: Registration DialogFragment
        TextView title = findViewById(R.id.my_title);

//        RegistrationDialogBuilder builder = new RegistrationDialogBuilder(this, getSupportFragmentManager(), R.id.frame_layout_container);

//        // Add registration options
//        builder.addNameOption(R.drawable.user, "Enter your name");
//        builder.addLastNameOption(R.drawable.user, "Enter your last name");
//        builder.addEmailOption(R.drawable.ic_mail, "Enter your email");
//        builder.addPasswordOption(R.drawable.ic_password, "Enter your password");
//
//        // Set button text and color
//        builder.setButtonText("Sign Up");
//        builder.setDialogRegisterTitle("Sign up to create account");
//        builder.setDialogColor(Color.parseColor("#FF8A80"));
//
//        builder.setOnRegistrationCompleteListener(new OnRegistrationCompleteListener() {
//            @Override
//            public void onRegistrationComplete() {
//                title.setVisibility(View.VISIBLE);
//            }
//        });
//
//        builder.showRegistrationDialog();

//        //Test: Rating app DialogFragment
//        String testPackageName = "com.whatsapp"; // Example package name
//        RatingAppDialogBuilder ratingAppDialogBuilder = new RatingAppDialogBuilder(
//                this,
//                getSupportFragmentManager(),
//                testPackageName,
//                4,
//                Color.parseColor("#9B0000"),
//                new RatingAppDialogCallback() {
//            @Override
//            public void onRatingSubmitted(float rating) {
//                //your code on rate submit
//                Log.d("User Rate app", "" + rating);
//            }
//        }, new RatingAppOnStoreDialogCallback() {
//            @Override
//            public void onStoreReference() {
//                //your code on store reference
//                Log.d("I'm in store", "hello");
//            }
//        });
//
//        ratingAppDialogBuilder.showRatingDialog();

        //Test: ProfileImagePicker Fragment
        ProfileImagePickerFragmentBuilder builder = new ProfileImagePickerFragmentBuilder(this, getSupportFragmentManager(), R.id.frame_layout_container, getPackageName(),-1);
        builder.showFragment();

    }


}