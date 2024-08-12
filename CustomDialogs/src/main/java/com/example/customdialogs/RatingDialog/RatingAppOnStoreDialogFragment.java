package com.example.customdialogs.RatingDialog;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.customdialogs.R;
import com.google.android.material.button.MaterialButton;

public class RatingAppOnStoreDialogFragment extends DialogFragment {

    private String packageName;
    private int dialogColor;

    private MaterialButton rate_now2_BTN;
    private MaterialButton may_be_later2_BTN;
    private View rate_on_store_dialog_template;
    private RatingAppOnStoreDialogCallback onStoreReferenceCallback = null;

    public RatingAppOnStoreDialogFragment(String packageName, int dialogColor, RatingAppOnStoreDialogCallback onStoreReferenceCallback) {
        this.packageName = packageName;
        this.dialogColor = dialogColor;
        this.onStoreReferenceCallback = onStoreReferenceCallback;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_rate_us_on_store, container, false); //inflate the dialog layaout (xml)

        findViews(view);
        setUpColor();
        setupButtons();

        return view;
    }

    private void findViews(View view) {
        rate_on_store_dialog_template = view.findViewById(R.id.rate_on_store_dialog_template);
        may_be_later2_BTN = view.findViewById(R.id.may_be_later2_BTN);
        rate_now2_BTN = view.findViewById(R.id.rate_now2_BTN);
    }

    private void setUpColor() {
        rate_on_store_dialog_template.setBackgroundColor(dialogColor);
    }

    private void setupButtons() {
        may_be_later2_BTN.setOnClickListener(v -> dismiss());
        rate_now2_BTN.setOnClickListener(v -> {
            // Implement logic to open app store
            openAppStore();
            if(onStoreReferenceCallback != null){
                onStoreReferenceCallback.onStoreReference();
            }
            dismiss();
        });
    }

    private void openAppStore() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
        }
    }
}
