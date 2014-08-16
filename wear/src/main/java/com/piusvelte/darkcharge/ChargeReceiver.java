package com.piusvelte.darkcharge;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by bemmanuel on 8/8/14.
 */
public class ChargeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_POWER_CONNECTED.equals(intent.getAction())) {
            ChargingActivity.start(context);
        } else if (Intent.ACTION_POWER_DISCONNECTED.equals(intent.getAction())) {
            Activity chargingActivity = ChargingActivity.getActivity();
            if (chargingActivity != null) chargingActivity.finish();
        }
    }
}
