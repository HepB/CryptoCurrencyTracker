package ru.lyubimov.cryptotracker;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by Alex on 28.01.2018.
 */

public class Preferences {
    private static final String PREF_SEARCH_QUERY = "query";
    private static final String PREF_SORT_TYPE = "sort";

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

    public static String getStoredSort(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_SORT_TYPE, CryptoCurrencyComparator.COMPARE_BY_RANK);
    }

    public static void setStoredSort(Context context, String sortType) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_SORT_TYPE, sortType)
                .apply();
    }


}
