package com.piusvelte.darkcharge.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by bemmanuel on 8/16/14.
 */
public class DataHelper {

    public static final String PREFS_NAME = "settings";
    public static final String PREF_ENABLED = "enabled";

    public static final String WEAR_PATH_ENABLED = "/enabled";

    public static final boolean ENABLED_DEFAULT = true;

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static boolean isEnabled(SharedPreferences sharedPreferences) {
        return sharedPreferences.getBoolean(PREF_ENABLED, ENABLED_DEFAULT);
    }

    public static void setEnabled(SharedPreferences sharedPreferences, boolean isEnabled) {
        sharedPreferences
                .edit()
                .putBoolean(PREF_ENABLED, isEnabled)
                .commit();
    }

    public static void syncEnabled(Context context, boolean isEnabled) {
        PutDataMapRequest dataMapRequest = PutDataMapRequest.create(WEAR_PATH_ENABLED);
        dataMapRequest.getDataMap().putBoolean(PREF_ENABLED, isEnabled);
        PutDataRequest request = dataMapRequest.asPutDataRequest();

        GoogleApiClient googleApiClient = new GoogleApiClient
                .Builder(context)
                .addApi(Wearable.API)
                .build();
        googleApiClient.connect();
        PendingResult<DataApi.DataItemResult> pendingResult = Wearable.DataApi
                .putDataItem(googleApiClient, request);
    }
}
