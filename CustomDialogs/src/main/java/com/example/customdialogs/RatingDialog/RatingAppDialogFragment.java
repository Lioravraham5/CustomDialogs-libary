package com.example.customdialogs.RatingDialog;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.customdialogs.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class RatingAppDialogFragment extends DialogFragment {

    private final long delay = 1000;

    private String packageName;
    private FragmentManager fragmentManager;
    private int dialogColor;
    private int starsLimitToRateOnStore;
    private RatingAppDialogCallback onRatingSubmittedCallback = null;
    private RatingAppOnStoreDialogCallback onStoreReferenceCallback = null;

    private ImageView rate_dialog_icon;
    private RatingBar rating_bar;
    private MaterialButton rate_now_BTN;
    private MaterialButton may_be_later_BTN;
    private MaterialTextView dialog_rate_us_confirmation_message;
    private MaterialTextView dialog_rate_us_please_take;
    private LinearLayout dialog_rate_us_layout_buttons;
    private View rate_on_app_dialog_template;

    public RatingAppDialogFragment(String packageName, FragmentManager fragmentManager, int starsLimitToRateOnStore,int dialogColor, RatingAppDialogCallback onRatingSubmittedCallback, RatingAppOnStoreDialogCallback onStoreReferenceCallback) {
        this.packageName = packageName;
        this.fragmentManager = fragmentManager;
        this.dialogColor = dialogColor;
        this.starsLimitToRateOnStore = starsLimitToRateOnStore;
        this.onRatingSubmittedCallback = onRatingSubmittedCallback;
        this.onStoreReferenceCallback = onStoreReferenceCallback;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_rate_us, container, false); //inflate the dialog layaout (xml)

        findViews(view);
        setUpColor();
        setupRatingBar();
        setupButtons();

        return view;
    }


    private void findViews(View view) {
        rate_on_app_dialog_template = view.findViewById(R.id.rate_on_app_dialog_template);
        may_be_later_BTN = view.findViewById(R.id.may_be_later_BTN);
        rate_now_BTN = view.findViewById(R.id.rate_now_BTN);
        rating_bar = view.findViewById(R.id.rating_bar);
        rate_dialog_icon = view.findViewById(R.id.rate_dialog_icon);
        dialog_rate_us_confirmation_message = view.findViewById(R.id.dialog_rate_us_confirmation_message);
        dialog_rate_us_please_take = view.findViewById(R.id.dialog_rate_us_please_take);
        dialog_rate_us_layout_buttons = view.findViewById(R.id.dialog_rate_us_layout_buttons);
    }

    private void setUpColor() {
        rate_on_app_dialog_template.setBackgroundColor(dialogColor);
        rating_bar.setProgressTintList(ColorStateList.valueOf(dialogColor));
    }

    private void setupRatingBar() {
        rating_bar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            updateIconBasedOnRating(rating);
        });
    }

    private void updateIconBasedOnRating(float rateValue) {
        if (rateValue <= 2) {
            rate_dialog_icon.setImageResource(R.drawable.one_and_two_star);
        } else if (rateValue <= 3) {
            rate_dialog_icon.setImageResource(R.drawable.three_star);
        } else if (rateValue <= 4) {
            rate_dialog_icon.setImageResource(R.drawable.four_star);
        } else {
            rate_dialog_icon.setImageResource(R.drawable.five_star);
        }
    }

    private void setupButtons() {
        may_be_later_BTN.setOnClickListener(v -> dismiss());

        rate_now_BTN.setOnClickListener(v -> {
            float userRating = rating_bar.getRating();
            if(onRatingSubmittedCallback != null){
                onRatingSubmittedCallback.onRatingSubmitted(userRating);
            }
            if (userRating >= starsLimitToRateOnStore && starsLimitToRateOnStore != -1){

                dismiss();
                RatingAppOnStoreDialogFragment ratingAppOnStoreDialog = new RatingAppOnStoreDialogFragment(packageName, dialogColor, onStoreReferenceCallback);
                ratingAppOnStoreDialog.setCancelable(false);
                ratingAppOnStoreDialog.show(fragmentManager, "RatingOnStoreDialog");
            }
            else {
                dialog_rate_us_please_take.setVisibility(View.GONE);
                rating_bar.setVisibility(View.GONE);
                dialog_rate_us_confirmation_message.setVisibility(View.VISIBLE);
                dialog_rate_us_layout_buttons.setVisibility(View.GONE);
                rate_dialog_icon.setImageResource(R.drawable.confirm);
                setCancelable(true);
            }

            // Dismiss the dialog after a delay
            new android.os.Handler().postDelayed(() -> dismiss(), delay); // 1000 milliseconds = 1 seconds
        });
    }


}
