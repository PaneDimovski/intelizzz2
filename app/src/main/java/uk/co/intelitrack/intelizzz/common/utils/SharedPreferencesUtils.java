package uk.co.intelitrack.intelizzz.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import uk.co.intelitrack.intelizzz.common.data.RecordTimeAlarm;
import uk.co.intelitrack.intelizzz.common.data.TimerAlarm;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class SharedPreferencesUtils {
    private final static String TIME_ALARM_KEY = "Time_alarm_key";
    private Context mContext;

    public SharedPreferencesUtils(Context mContext) {
        this.mContext = mContext;
    }

    public String getSharedPreferencesString(String keySharedPreferences) {
        return PreferenceManager.getDefaultSharedPreferences(mContext)
                .getString(keySharedPreferences, "");
    }

    public Integer getSharedPreferencesInt(String keySharedPreferences, int alternative) {
        return PreferenceManager.getDefaultSharedPreferences(mContext)
                .getInt(keySharedPreferences, alternative);
    }

    public boolean getSharedPreferencesBoolean(String keySharedPreferences) {
        return PreferenceManager.getDefaultSharedPreferences(mContext)
                .getBoolean(keySharedPreferences, false);
    }

    public void setSharedPreferencesString(String keySharedPreferences, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(keySharedPreferences, value);
        editor.commit();
    }

    public void setSharedPreferencesInt(String keySharedPreferences, int value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(keySharedPreferences, value);
        editor.commit();
    }

    public void setSharedPreferencesBoolean(String keySharedPreferences, boolean value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(keySharedPreferences, value);
        editor.commit();
    }

    public void addTimer(TimerAlarm alarm) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        Gson gson = new Gson();
        RecordTimeAlarm recordTimeAlarm = gson.fromJson(preferences.getString(TIME_ALARM_KEY,
                ""), RecordTimeAlarm.class);
        if (recordTimeAlarm == null || recordTimeAlarm.getAlarms() == null) {
            recordTimeAlarm = new RecordTimeAlarm(new ArrayList<>());
        }
        recordTimeAlarm.getAlarms().add(alarm);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TIME_ALARM_KEY, gson.toJson(recordTimeAlarm));
        editor.commit();
    }

    public List<TimerAlarm> getTimers() {
        List<TimerAlarm> results = new ArrayList<>();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        RecordTimeAlarm recordTimeAlarm = new Gson().fromJson(preferences.getString(TIME_ALARM_KEY,
                ""), RecordTimeAlarm.class);
        if (recordTimeAlarm != null) {
            results = recordTimeAlarm.getAlarms();
        }
        return results;
    }

    public TimerAlarm getTimer(String id) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        RecordTimeAlarm recordTimeAlarm = new Gson().fromJson(preferences.getString(TIME_ALARM_KEY,
                ""), RecordTimeAlarm.class);
        if (recordTimeAlarm != null) {
            for (TimerAlarm timerAlarm : recordTimeAlarm.getAlarms()) {
                if (timerAlarm.getId().equals(id)) {
                    return timerAlarm;
                }
            }
        }
        return null;
    }

    public void removeTimer(String id) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        RecordTimeAlarm recordTimeAlarm = new Gson().fromJson(preferences.getString(TIME_ALARM_KEY,
                ""), RecordTimeAlarm.class);
        if (recordTimeAlarm != null) {
            for (int i = 0; i < recordTimeAlarm.getAlarms().size(); i++) {
                if (recordTimeAlarm.getAlarms().get(i).getId().equals(id)) {
                    recordTimeAlarm.getAlarms().remove(i);
                    return;
                }
            }
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TIME_ALARM_KEY, new Gson().toJson(recordTimeAlarm));
        editor.commit();
    }

}