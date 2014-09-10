package info.nerull7.nap.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import info.nerull7.nap.AlarmIntent;

/**
 * Created by nerull7 on 2014-09-05.
 */
public class NapActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = AlarmIntent.getAlarmIntent(this);
        startActivity(intent);
        finish();
    }
}
