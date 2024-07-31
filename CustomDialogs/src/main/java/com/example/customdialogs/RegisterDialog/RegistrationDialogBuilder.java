package com.example.customdialogs.RegisterDialog;

import android.content.Context;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class RegistrationDialogBuilder {

    private Context context;
    private List<com.example.customdialogs.RegisterDialog.RegistrationOption> options;
    private FragmentManager fragmentManager;
    private int containerId; // ID of the container to host the fragment
    private String buttonText = null;
    private int dialogColor = -1;
    private OnRegistrationCompleteListener onRegistrationCompleteListener;

    // Constructor to initialize necessary components
    public RegistrationDialogBuilder(Context context, FragmentManager fragmentManager, int containerId) {
        this.context = context;
        this.containerId = containerId;
        this.fragmentManager = fragmentManager;
        this.options = new ArrayList<>();
    }

    public RegistrationDialogBuilder setOnRegistrationCompleteListener(OnRegistrationCompleteListener onRegistrationCompleteListener) {
        this.onRegistrationCompleteListener = onRegistrationCompleteListener;
        return this;
    }

    public RegistrationDialogBuilder setContainerId(int containerId) {
        this.containerId = containerId;
        return this;
    }

    public RegistrationDialogBuilder setButtonText(String buttonText) {
        this.buttonText = buttonText;
        return this;

    }

    public RegistrationDialogBuilder setDialogColor(int dialogColor) {
        this.dialogColor = dialogColor;
        return this;

    }

    // Method to add registration options
    public RegistrationDialogBuilder addNameOption(int iconResId, String hint) {
        options.add(new RegistrationOption(SupportedRegisterOptions.AllSupportedOption.NAME, iconResId, hint));
        return this;
    }

    public RegistrationDialogBuilder addLastNameOption(int iconResId, String hint) {
        options.add(new RegistrationOption(SupportedRegisterOptions.AllSupportedOption.LAST_NAME, iconResId, hint));
        return this;
    }

    public RegistrationDialogBuilder addPasswordOption(int iconResId, String hint) {
        options.add(new RegistrationOption(SupportedRegisterOptions.AllSupportedOption.PASSWORD, iconResId, hint));
        return this;
    }

    public RegistrationDialogBuilder addEmailOption(int iconResId, String hint) {
        options.add(new RegistrationOption(SupportedRegisterOptions.AllSupportedOption.EMAIL, iconResId, hint));
        return this;
    }

    public RegistrationDialogBuilder addPhoneNumberOption(int iconResId, String hint) {
        options.add(new RegistrationOption(SupportedRegisterOptions.AllSupportedOption.PHONE_NUMBER, iconResId, hint));
        return this;
    }

    public RegistrationDialogBuilder addCityOption(int iconResId, String hint) {
        options.add(new RegistrationOption(SupportedRegisterOptions.AllSupportedOption.CITY, iconResId, hint));
        return this;
    }

    public RegistrationDialogBuilder addAddressOption(int iconResId, String hint) {
        options.add(new RegistrationOption(SupportedRegisterOptions.AllSupportedOption.ADDRESS, iconResId, hint));
        return this;
    }

    // Method to show the registration fragment
    public void show() {

        RegistrationDialogFragment registrationFragment;

        //The developer change the color dialog and text of the button
        if(dialogColor != -1 && buttonText != null)
            registrationFragment = new RegistrationDialogFragment(options, buttonText, dialogColor);

        //The developer change only the color dialog of the button
        else if (dialogColor != -1 && buttonText == null)
            registrationFragment = new RegistrationDialogFragment(options, dialogColor);

        //The developer change only the text of the button
        else if (dialogColor == -1 && buttonText != null)
            registrationFragment = new RegistrationDialogFragment(options, buttonText);

        //The developer didn't change the color dialog and text of the button
        else
            registrationFragment = new RegistrationDialogFragment(options);

        //set the callback
        registrationFragment.setOnRegistrationCompleteListener(onRegistrationCompleteListener);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(containerId, registrationFragment);
        transaction.commit();
    }

}
