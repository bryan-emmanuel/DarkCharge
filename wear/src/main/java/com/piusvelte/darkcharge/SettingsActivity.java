package com.piusvelte.darkcharge;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.piusvelte.darkcharge.utils.DataHelper;

/**
 * Created by bemmanuel on 8/10/14.
 */
public class SettingsActivity extends Activity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private SharedPreferences mSharedPreferences;
    private CheckBox mChkEnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mSharedPreferences = DataHelper.getSharedPreferences(this);
        boolean isEnabled = DataHelper.isEnabled(mSharedPreferences);

        mChkEnable = (CheckBox) findViewById(R.id.btn_toggle);
        mChkEnable.setChecked(isEnabled);
        mChkEnable.setText(isEnabled ? R.string.settings_enabled : R.string.settings_disabled);
        mChkEnable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.setText(isChecked ? R.string.settings_enabled : R.string.settings_disabled);
            }
        });

        mChkEnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isEnabled = ((CheckBox) v).isChecked();
                DataHelper.setEnabled(mSharedPreferences, isEnabled);
                DataHelper.syncEnabled(v.getContext(), isEnabled);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        mSharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (DataHelper.PREF_ENABLED.equals(key)) {
            mChkEnable.setChecked(DataHelper.isEnabled(sharedPreferences));
        }
    }
}
