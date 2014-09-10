package info.nerull7.nap;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.provider.AlarmClock;

import java.util.Calendar;

/**
 * Created by nerull7 on 2014-09-03.
 */
public class AlarmIntent extends IntentService {
    private static final String INTENT_SERVICE_NAME = "One Button Nap";

    public AlarmIntent() {
        super(INTENT_SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        setAlarm();
    }

    private void setAlarm(){
        Intent intent = getAlarmIntent(getApplicationContext());
        startActivity(intent);
    }

    public static Intent getAlarmIntent(Context context){
        Calendar calendar = Calendar.getInstance();
        int shiftTime = PreferenceManager.getDefaultSharedPreferences(context).getInt(Settings.NAP_TIME, 30);
        calendar.add(Calendar.MINUTE, shiftTime);

        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(AlarmClock.EXTRA_HOUR, calendar.get(Calendar.HOUR_OF_DAY));
        intent.putExtra(AlarmClock.EXTRA_MINUTES, calendar.get(Calendar.MINUTE));
        intent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);

        return intent;
    }
}
