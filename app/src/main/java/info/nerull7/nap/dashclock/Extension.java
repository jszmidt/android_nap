package info.nerull7.nap.dashclock;

import android.content.Intent;
import android.preference.PreferenceManager;

import com.google.android.apps.dashclock.api.DashClockExtension;
import com.google.android.apps.dashclock.api.ExtensionData;

import info.nerull7.nap.R;
import info.nerull7.nap.Settings;
import info.nerull7.nap.ui.NapActivity;

/**
 * Created by nerull7 on 10.09.14.
 */
public class Extension extends DashClockExtension {
    private static final String TAG = "OneButtonNap";
    private static final String STATUS = "Nap";

    @Override
    protected void onInitialize(boolean isReconnect) {
        super.onInitialize(isReconnect);
        addWatchContentUris(new String[] {"content://info.nerull7.nap.settingsprovider"});
    }

    @Override
    protected void onUpdateData(int reason) {
        int napTime = PreferenceManager.getDefaultSharedPreferences(this).getInt(Settings.NAP_TIME, 30);
        String title = getApplicationContext().getResources().getString(R.string.start_nap, napTime);
        String status = napTime+"m";

        publishUpdate(new ExtensionData()
                        .visible(true)
                        .icon(R.drawable.ic_stat_access_alarms)
                        .status(status)
                        .expandedTitle(title)
                        .contentDescription("Completely different text for accessibility if needed.")
                        .clickIntent(new Intent(getApplicationContext(), NapActivity.class))
        );
    }
}
