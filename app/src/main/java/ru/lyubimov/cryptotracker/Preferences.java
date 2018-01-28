package ru.lyubimov.cryptotracker;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by Alex on 28.01.2018.
 */

public class Preferences {
    private static final String PREF_SEARCH_QUERY = "query";

    public static String getStoredQuery(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_SEARCH_QUERY, null);
    }

    public static void setStoredQuery(Context context, String query) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_SEARCH_QUERY, query)
                .apply();
    }


}
