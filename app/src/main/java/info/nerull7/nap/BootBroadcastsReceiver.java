package info.nerull7.nap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;

/**
 * Created by nerull7 on 10.09.14.
 */
public class BootBroadcastsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHandler notificationHandler = new NotificationHandler(context);

        if (PreferenceManager.getDefaultSharedPreferences(context).getBoolean(Settings.ENABLE_NOTIFICATION, Settings.ENABLE_NOTIFICATION_DEFAULT)) {
            notificationHandler.publishNotification();
        }
    }
}
