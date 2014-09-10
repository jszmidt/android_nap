package info.nerull7.nap;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.*;

/**
 * Created by nerull7 on 2014-09-03.
 */
public class NotificationHandler {
    private static final int notificationID = 1;
    private static final int NOTIFICATION_ICON = R.drawable.ic_stat_access_alarms;
    private static final int NOTIFICATION_ICON_ACTION_WEARABLE = R.drawable.ic_action_access_alarms;

    private Context context;
    private int priority;
    private boolean localOnly;

    public NotificationHandler(Context context) {
        this.context = context;

        priority = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(context).getString(Settings.NOTIFICATION_PRIORITY, Settings.NOTIFICATION_PRIORITY_DEFAULT));
        localOnly = !PreferenceManager.getDefaultSharedPreferences(context).getBoolean(Settings.ENABLE_ANDROID_WEAR, true);
    }

    private int getNapTime() {
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);

        return preference.getInt(Settings.NAP_TIME, Settings.NAP_TIME_DEFAULT);
    }

    public void publishNotification() {
        Intent alarmIntent = new Intent(context, AlarmIntent.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, alarmIntent, 0);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(notificationID, getNotification(pendingIntent, getWearableNotification(pendingIntent)));
    }

    public void removeNotification(){
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(notificationID);
    }

    public void changePriority(int newPriority){
        priority = newPriority;
        publishNotification();
    }

    private Notification getNotification(PendingIntent pendingIntent, WearableExtender wearableExtender) {
        Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(NOTIFICATION_ICON)
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText(context.getResources().getString(R.string.start_nap, getNapTime()))
                .setPriority(priority)
                .setContentIntent(pendingIntent)
                .setLocalOnly(localOnly);
        if(!localOnly){
            builder.extend(wearableExtender);
        }
        return builder.build();
    }

    private WearableExtender getWearableNotification(PendingIntent pendingIntent) {
        Action action = new NotificationCompat.Action.Builder(NOTIFICATION_ICON_ACTION_WEARABLE, null, pendingIntent).build();
        WearableExtender wearable = new WearableExtender()
                .setHintHideIcon(true)
                .addAction(action)
                .setContentAction(0);

        return wearable;
    }

    public void changeAndroidWearSupport(boolean enable){
        localOnly = !enable;
        publishNotification();
    }
}