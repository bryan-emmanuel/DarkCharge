package com.piusvelte.darkcharge;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

/**
 * Created by bemmanuel on 8/10/14.
 */
public class SettingsActivity extends Activity {

    public static final String PREFS_NAME = "settings";
    public static final String PREF_ENABLED = "enabled";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        boolean isEnabled = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).getBoolean(PREF_ENABLED, true);

        CheckBox btnToggle = (CheckBox) findViewById(R.id.btn_toggle);
        btnToggle.setChecked(isEnabled);
        btnToggle.setText(isEnabled ? R.string.settings_enabled : R.string.settings_disabled);
        btnToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                        .edit()
                        .putBoolean(PREF_ENABLED, isChecked)
                        .commit();
                finish();
            }
        });
    }
}
