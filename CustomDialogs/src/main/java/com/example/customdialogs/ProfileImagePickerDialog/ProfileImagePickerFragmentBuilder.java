package com.example.customdialogs.ProfileImagePickerDialog;

import android.content.Context;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.customdialogs.RegisterDialog.OnRegistrationCompleteListener;
import com.example.customdialogs.RegisterDialog.RegistrationDialogBuilder;
import com.example.customdialogs.RegisterDialog.RegistrationDialogFragment;
import com.example.customdialogs.RegisterDialog.RegistrationOption;
import com.example.customdialogs.RegisterDialog.SupportedRegisterOptions;

import java.util.ArrayList;
import java.util.List;

public class ProfileImagePickerFragmentBuilder {
    private Context context;
    private FragmentManager fragmentManager;
    private int containerId; // ID of the container to host the fragment
    private String packageName;
    private int customUserImage;

    // Constructor to initialize necessary components
    public ProfileImagePickerFragmentBuilder(Context context, FragmentManager fragmentManager, int containerId, String packageName, int customUserImage) {
        this.context = context;
        this.containerId = containerId;
        this.fragmentManager = fragmentManager;
        this.packageName = packageName;
        this.customUserImage = customUserImage;
    }


    // Method to show the fragment
    public void showFragment() {

        ProfileImagePickerFragment profileImagePickerFragment = new ProfileImagePickerFragment(context, packageName, customUserImage);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(containerId, profileImagePickerFragment);
        transaction.commit();
    }
}
