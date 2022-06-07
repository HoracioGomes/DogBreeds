package com.example.dogbreedsapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class SharedPreferencesHelper {
    private static final String PREF_TIME = "Pref time";
    private static SharedPreferencesHelper instance;
    private SharedPreferences prefs;

    private SharedPreferencesHelper(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public SharedPreferencesHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesHelper(context);
        }
        return instance;
    }

    public void saveUpdateTime(Long time) {
        prefs.edit().putLong(PREF_TIME, time);
    }

    public Long getUpdatedTime() {
        return prefs.getLong(PREF_TIME, 0);
    }
}
