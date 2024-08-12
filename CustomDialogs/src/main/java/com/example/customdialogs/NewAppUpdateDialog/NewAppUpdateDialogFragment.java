package com.example.customdialogs.NewAppUpdateDialog;

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
import com.google.android.material.textview.MaterialTextView;

public class NewAppUpdateDialogFragment extends DialogFragment {

    private MaterialTextView new_app_update_main_title;
    private MaterialTextView new_app_update_second_title;
    private MaterialButton new_app_update_update_now_BTN;
    private MaterialButton new_app_update_may_be_later_BTN;
    private View new_app_update_dialog_template;

    private String packageName;
    private String latestVersion;
    private String currentVersion;
    private int dialogColor;

    public NewAppUpdateDialogFragment(String packageName, int dialogColor){
        this.packageName = packageName;
        this.dialogColor = dialogColor;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_new_app_update, container, false); //inflate the dialog layaout (xml)

        findViews(view);
        setUpColor();
        setupButtons();

        return view;
    }

    private void findViews(View view) {
        new_app_update_main_title = view.findViewById(R.id.new_app_update_main_title);
        new_app_update_second_title = view.findViewById(R.id.new_app_update_second_title);
        new_app_update_update_now_BTN = view.findViewById(R.id.new_app_update_update_now_BTN);
        new_app_update_may_be_later_BTN = view.findViewById(R.id.new_app_update_may_be_later_BTN);
        new_app_update_dialog_template = view.findViewById(R.id.new_app_update_dialog_template);
    }

    private void setUpColor() {
        new_app_update_dialog_template.setBackgroundColor(dialogColor);
    }

    private void setupButtons() {
        new_app_update_may_be_later_BTN.setOnClickListener(v -> dismiss());
        new_app_update_update_now_BTN.setOnClickListener(v -> {
            // Implement logic to open app store
            openAppStore();
//            if(onStoreReferenceCallback != null){
//                onStoreReferenceCallback.onStoreReference();
//            }
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
