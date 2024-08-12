package com.example.customdialogs.RatingDialog;

import android.content.Context;
import android.graphics.Color;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.example.customdialogs.R;

public class RatingAppDialogBuilder {

    private String packageName;
    private FragmentManager fragmentManager;
    private int dialogColor;
    private int starsLimitToRateOnStore;
    private RatingAppDialogCallback onRatingSubmittedCallback = null;
    private RatingAppOnStoreDialogCallback onStoreReferenceCallback = null;


    public RatingAppDialogBuilder(Context context, FragmentManager fragmentManager, String packageName, int starsLimitToRateOnStore, int dialogColor, RatingAppDialogCallback onRatingSubmittedCallback, RatingAppOnStoreDialogCallback onStoreReferenceCallback) {
        this.fragmentManager = fragmentManager;
        this.packageName = packageName;
        this.starsLimitToRateOnStore = starsLimitToRateOnStore;

        //color defined by developer
        if(dialogColor != -1)
            this.dialogColor = dialogColor;
        else{
            // Get the actual color value from the resource ID
            this.dialogColor = ContextCompat.getColor(context, R.color.dialog_color_1);
        }

        //callback defined by developer
        if(onRatingSubmittedCallback != null)
            this.onRatingSubmittedCallback = onRatingSubmittedCallback;

        //callback defined by developer
        if(onStoreReferenceCallback != null)
            this.onStoreReferenceCallback = onStoreReferenceCallback;
    }

    public void showRatingDialog() {
        RatingAppDialogFragment dialog = new RatingAppDialogFragment(packageName, fragmentManager, starsLimitToRateOnStore,dialogColor, onRatingSubmittedCallback, onStoreReferenceCallback);
        dialog.setCancelable(false);
        dialog.show(fragmentManager, "RatingDialog");
    }
}
