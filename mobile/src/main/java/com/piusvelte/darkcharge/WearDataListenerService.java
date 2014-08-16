package com.piusvelte.darkcharge;

import android.content.SharedPreferences;

import com.google.android.gms.common.data.FreezableUtils;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.WearableListenerService;
import com.piusvelte.darkcharge.utils.DataHelper;

import java.util.List;

/**
 * Created by bemmanuel on 8/16/14.
 */
public class WearDataListenerService extends WearableListenerService {
    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        super.onDataChanged(dataEvents);

        final List<DataEvent> events = FreezableUtils.freezeIterable(dataEvents);
        SharedPreferences sharedPreferences = DataHelper.getSharedPreferences(this);

        for (DataEvent event : events) {
            DataItem dataItem = event.getDataItem();

            if (DataHelper.WEAR_PATH_ENABLED.equals(dataItem.getUri().getPath())) {
                DataMapItem dataMapItem = DataMapItem.fromDataItem(dataItem);
                boolean isEnabled = dataMapItem.getDataMap().getBoolean(DataHelper.PREF_ENABLED, true);
                DataHelper.setEnabled(sharedPreferences, isEnabled);
            }
        }

    }
}
