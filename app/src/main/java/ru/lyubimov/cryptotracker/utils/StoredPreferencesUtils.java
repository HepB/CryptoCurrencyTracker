package ru.lyubimov.cryptotracker.utils;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by Alex on 28.01.2018.
 */

public final class StoredPreferencesUtils {
    private static final String PREF_SEARCH_QUERY = "query";
    private static final String PREF_SORT_TYPE = "sortType";

    private StoredPreferencesUtils() {}

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

    public static int getStoredSort(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(PREF_SORT_TYPE, 0);
    }

    public static void setStoredSort(Context context, int sortType) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(PREF_SORT_TYPE, sortType)
                .apply();
    }
}
