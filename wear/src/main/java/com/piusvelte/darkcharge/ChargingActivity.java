package com.piusvelte.darkcharge;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.piusvelte.darkcharge.utils.DataHelper;

/**
 * Created by bemmanuel on 8/10/14.
 */
public class ChargingActivity extends Activity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Nullable
    private static Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent != null) {
            // if discharging, then screen on
            if (Intent.ACTION_POWER_DISCONNECTED.equals(intent.getAction())) {
                finish();
                return;
            }
        }

        if (mActivity != null) mActivity.finish();
        mActivity = this;

        setContentView(R.layout.activity_charging);

        // hold a lock
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // set the window dim
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_OFF;
        getWindow().setAttributes(layoutParams);

        Button btnFinish = (Button) findViewById(R.id.btn_finish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScreenReceiver.register(getApplicationContext());
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        DataHelper.getSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        DataHelper.getSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
        // this should always finish, so if it isn't, it's because the activity is being backgrounded
        if (!isFinishing()) start(getApplicationContext());
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mActivity = null;
        super.onDestroy();
    }

    @Nullable
    public static Activity getActivity() {
        return mActivity;
    }

    public static void start(Context context) {
        // start the blank activity if enabled
        if (DataHelper.isEnabled(context)) {
            context.startActivity(new Intent(context, ChargingActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (DataHelper.PREF_ENABLED.equals(key) && !DataHelper.isEnabled(sharedPreferences)) {
            finish();
        }
    }
}
