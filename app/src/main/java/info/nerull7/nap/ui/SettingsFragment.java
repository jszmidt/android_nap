package info.nerull7.nap.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;

import info.nerull7.nap.NotificationHandler;
import info.nerull7.nap.R;
import info.nerull7.nap.Settings;

/**
 * Created by nerull7 on 2014-09-03.
 */
public class SettingsFragment extends PreferenceFragment implements NumberPickerDialog.OnNumberSetListener, Preference.OnPreferenceChangeListener {
    private Preference napTime;
    private CheckBoxPreference enableNotification;
    private CheckBoxPreference enableAndroidWear;
    private ListPreference notificationPriority;

    private String numberPreference;
    private SharedPreferences preferences;
    private NotificationHandler notificationHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        notificationHandler = new NotificationHandler(getActivity());

        loadPrefs();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(Settings.ENABLE_NOTIFICATION, Settings.ENABLE_NOTIFICATION_DEFAULT)) {
            notificationHandler.publishNotification();
        }
    }

    private void loadPrefs() {
        napTime = findPreference(Settings.NAP_TIME);
        enableNotification = (CheckBoxPreference) findPreference(Settings.ENABLE_NOTIFICATION);
        enableAndroidWear = (CheckBoxPreference) findPreference(Settings.ENABLE_ANDROID_WEAR);
        notificationPriority = (ListPreference) findPreference(Settings.NOTIFICATION_PRIORITY);

        enableNotification.setOnPreferenceChangeListener(this);
        enableAndroidWear.setOnPreferenceChangeListener(this);
        notificationPriority.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (preference == napTime) {
            new NumberPickerDialog(getActivity(), this, preferences.getInt(Settings.NAP_TIME, 30), 0, 60, R.string.nap_time).show();
            numberPreference = Settings.NAP_TIME;
        } else {
            return false;
        }
        return true;
    }

    @Override
    public void onNumberSet(int number) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(numberPreference, number);
        editor.apply();
        notificationHandler.publishNotification();
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        if(preference == enableNotification){
            if((Boolean) o){
                notificationHandler.publishNotification();
            } else {
                notificationHandler.removeNotification();
            }
        } else if(preference == notificationPriority){
            notificationHandler.changePriority(Integer.parseInt((String) o));
        } else if(preference == enableAndroidWear){
            notificationHandler.changeAndroidWearSupport((Boolean) o);
        } else {
            return false;
        }
        return true;
    }
}