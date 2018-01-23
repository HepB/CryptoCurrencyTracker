package ru.lyubimov.cryptotracker;

import android.support.v4.app.Fragment;

public class CryptoTrackerActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return CryptoCurListFragment.newInstance();
    }
}
