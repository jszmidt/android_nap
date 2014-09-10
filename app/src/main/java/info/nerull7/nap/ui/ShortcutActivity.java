package info.nerull7.nap.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import info.nerull7.nap.AlarmIntent;
import info.nerull7.nap.R;

/**
 * Created by nerull7 on 2014-09-05.
 */
public class ShortcutActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent shortcutIntent=new Intent(this, info.nerull7.nap.ui.NapActivity.class);
        final Intent.ShortcutIconResource iconResource = Intent.ShortcutIconResource.fromContext(this, R.drawable.ic_stat_access_alarms);
        final Intent intent=new Intent();
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT,shortcutIntent);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getResources().getString(R.string.take_nap));
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconResource);
        setResult(RESULT_OK,intent);
        finish();
    }
}
