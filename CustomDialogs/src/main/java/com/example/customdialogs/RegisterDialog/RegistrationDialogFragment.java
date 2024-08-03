package com.example.customdialogs.RegisterDialog;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.customdialogs.R;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class RegistrationDialogFragment extends DialogFragment {
    private static String DEFAULT_DIALOG_TITLE = "Sign in";
    private static String DEFAULT_BUTTON_TEXT = "Sign in";
    private static String DEFAULT_DIALOG_COLOR = "#42A5F5"; //blue 400 primary

    private List<RegistrationOption> options;
    private String buttonText = DEFAULT_BUTTON_TEXT;
    private String dialogRegisterTitle = DEFAULT_DIALOG_TITLE;
    private int dialogColor = Color.parseColor(DEFAULT_DIALOG_COLOR);
    private OnRegistrationCompleteListener onRegistrationCompleteListener;

    public RegistrationDialogFragment(String dialogRegisterTitle, List<RegistrationOption> options) {
        this.dialogRegisterTitle = dialogRegisterTitle;
        this.options = options;
    }

    public RegistrationDialogFragment(String dialogRegisterTitle, List<RegistrationOption> options, String buttonText, int buttonColor) {
        this.dialogRegisterTitle = dialogRegisterTitle;
        this.options = options;
        this.buttonText = buttonText;
        this.dialogColor = buttonColor;
    }

    public RegistrationDialogFragment(String dialogRegisterTitle, List<RegistrationOption> options, int dialogColor) {
        this.dialogRegisterTitle = dialogRegisterTitle;
        this.options = options;
        this.dialogColor = dialogColor;
    }

    public RegistrationDialogFragment(String dialogRegisterTitle, List<RegistrationOption> options, String buttonText) {
        this.dialogRegisterTitle = dialogRegisterTitle;
        this.options = options;
        this.buttonText = buttonText;
    }

    public void setOnRegistrationCompleteListener(OnRegistrationCompleteListener onRegistrationCompleteListener) {
        this.onRegistrationCompleteListener = onRegistrationCompleteListener;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public void setDialogColor(int dialogColor) {
        this.dialogColor = dialogColor;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_registration, container, false); //inflate the dialog layaout (xml)
        LinearLayout card_inner_layout; //Finds the LinearLayout int the xml where the registration options will be added.
        card_inner_layout = view.findViewById(R.id.card_inner_layout);

        for (RegistrationOption option : options) {
            View field = inflater.inflate(R.layout.item_registration_option, card_inner_layout, false);

            createEditText(field, option); //create editText in field
            createIcon(field, option); // create Icon in field

            card_inner_layout.addView(field);
        }

        //set custom dialog "sign in" text
        TextView dialog_register_title = card_inner_layout.findViewById(R.id.dialog_register_title);
        dialog_register_title.setText(dialogRegisterTitle);
        dialog_register_title.setTextColor(dialogColor);

        //set custom dialog button
        Button registerButton = createButtonProgrammatically();

        // Add the button to the layout
        card_inner_layout.addView(registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateAllOptions()) {
                    //perform the callback
                    if(onRegistrationCompleteListener != null){
                        Log.d("RegistrationFragment", "All options are valid. Proceed with registration.");
                        onRegistrationCompleteListener.onRegistrationComplete();
                    }

                } else {
                    // Show error or handle invalid input
                    Log.d("RegistrationFragment", "Invalid input detected.");
                }
            }
        });

        return view;
    }

    private void createIcon(View field, RegistrationOption option) {
        ImageView icon = field.findViewById(R.id.option_icon);
        icon.setImageResource(option.getIconResId());
        icon.setColorFilter(dialogColor);
    }

    private void createEditText(View field, RegistrationOption option) {
        EditText editText = field.findViewById(R.id.option_edit_text);
        // Retrieve the drawable background and cast it to GradientDrawable
        GradientDrawable drawable = (GradientDrawable) editText.getBackground();
        drawable.setStroke(2, dialogColor); // 2 is the stroke width in pixels
        editText.setHint(option.getHint());
        editText.setTag(option.getOptionType());
        if (option.getOptionType() == SupportedRegisterOptions.AllSupportedOption.PASSWORD){
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    private boolean validateAllOptions() {
        boolean allValid = true;
        for (RegistrationOption option : options) {
            EditText editText = getView().findViewWithTag(option.getOptionType());
            String userInput = editText.getText().toString();
            if (!isOptionValid(option, userInput, editText)) {
                allValid = false;
            }
        }
        return allValid;
    }

    private boolean isOptionValid(RegistrationOption option, String userInput, EditText editText) {
        boolean isValid;
        switch (option.getOptionType()) {
            case NAME:
                isValid = FormValidator.isValidName(userInput);
                if(!isValid)
                    editText.setError("Name must contains only letters, spaces, and hyphens");
                return isValid;

            case LAST_NAME:
                isValid = FormValidator.isValidName(userInput);
                if(!isValid)
                    editText.setError("Last name must contains only letters, spaces, and hyphens");
                return isValid;

            case CITY:
                isValid = FormValidator.isValidName(userInput);
                if(!isValid)
                    editText.setError("City must contains only letters, spaces, and hyphens");
                return isValid;

            case PASSWORD:
                isValid = FormValidator.isValidPassword(userInput);
                if(!isValid)
                    editText.setError("Password must contains at least 8 characters, one uppercase, one lowercase, one number");
                return isValid;

            case EMAIL:
                isValid = FormValidator.isValidEmail(userInput);
                if(!isValid)
                    editText.setError("Invalid Email");
                return isValid;

            case PHONE_NUMBER:
                isValid = FormValidator.isValidPhoneNumber(userInput);
                if(!isValid)
                    editText.setError("Invalid Phone number");
                return isValid;

            case ADDRESS:
                isValid = FormValidator.isValidAddress(userInput);
                if(!isValid)
                    editText.setError("Address must contains only letters, hyphens, numbers, and spaces)");
                return isValid;

            default:
                return true;
        }
    }

    private MaterialButton createButtonProgrammatically(){
        MaterialButton registerButton = new MaterialButton(getContext());
        registerButton.setText(buttonText);
        registerButton.setBackgroundColor(dialogColor);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL; // Center horizontally within the parent
        layoutParams.setMargins(16, 16, 16, 16); // Set margin

        registerButton.setLayoutParams(layoutParams);

        return registerButton;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
