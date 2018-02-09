package ru.lyubimov.cryptotracker;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by Alex on 28.01.2018.
 */

class StoredPreferences {
    private static final String PREF_SEARCH_QUERY = "query";
    private static final String PREF_SORT_TYPE = "sortType";

    static String getStoredQuery(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_SEARCH_QUERY, null);
    }

    static void setStoredQuery(Context context, String query) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_SEARCH_QUERY, query)
                .apply();
    }

    static int getStoredSort(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(PREF_SORT_TYPE, 0);
    }

    static void setStoredSort(Context context, int sortType) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(PREF_SORT_TYPE, sortType)
                .apply();
    }
}
