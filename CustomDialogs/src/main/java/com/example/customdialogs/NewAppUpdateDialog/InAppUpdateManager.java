package com.example.customdialogs.NewAppUpdateDialog;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;

public class InAppUpdateManager {

    private static final String TAG = "InAppUpdateManager";
    private static final int REQUEST_CODE_UPDATE = 100; //to identify what kind of results come from

    private UpdateListener updateListener;

    private AppUpdateManager appUpdateManager;
    private Activity activity;

    public InAppUpdateManager(Activity activity) {
        this.activity = activity;
        appUpdateManager = AppUpdateManagerFactory.create(activity);
    }

    public void checkForUpdate() {
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            //check if there is a available update
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                if (updateListener != null) {
                    updateListener.onUpdateAvailable();
                }
                startUpdate(appUpdateInfo);
            }
        });
    }

    private void startUpdate(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.FLEXIBLE, activity, REQUEST_CODE_UPDATE);
        } catch (IntentSender.SendIntentException e) {
            Log.e(TAG, "Error starting update flow", e);
        }
    }

    public void onResume() {
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.installStatus() == com.google.android.play.core.install.model.InstallStatus.DOWNLOADED) {
                notifyUserToCompleteUpdate();
            }
        });
    }

    private void notifyUserToCompleteUpdate() {
        // Implement your own UI to notify the user about the completed download
        // For example, you can show a dialog or a snackbar
        // When the user confirms, call completeUpdate()
        if (updateListener != null) {
            updateListener.onUpdateDownloaded();
        }
    }

    public void completeUpdate() {
        appUpdateManager.completeUpdate();
    }

    public void setUpdateListener(UpdateListener listener) {
        this.updateListener = listener;
    }

}
