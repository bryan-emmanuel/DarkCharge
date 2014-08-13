package com.piusvelte.darkcharge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by bemmanuel on 8/10/14.
 */
public class ScreenReceiver extends BroadcastReceiver {

    public static void register(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);

        ScreenReceiver receiver = new ScreenReceiver();
        context.getApplicationContext().registerReceiver(receiver, filter);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_POWER_DISCONNECTED.equals(intent.getAction())) {
            context.unregisterReceiver(this);
        } else if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
            ChargingActivity.start(context);
            context.unregisterReceiver(this);
        }
    }
}
