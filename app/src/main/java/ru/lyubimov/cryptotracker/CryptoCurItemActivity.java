package ru.lyubimov.cryptotracker;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import ru.lyubimov.cryptotracker.model.CryptoCurrency;

/**
 * Created by Alex on 28.01.2018.
 */

public class CryptoCurItemActivity extends SingleFragmentActivity {

    private static final String EXTRA_CRYPTO_CURRENCY = "ru.lyubimov.cryptotracker.crypto_currency";

    public static Intent newIntent(Context packageContext, CryptoCurrency cryptoCurrency) {
        Intent intent = new Intent(packageContext, CryptoCurItemActivity.class);
        intent.putExtra(EXTRA_CRYPTO_CURRENCY, cryptoCurrency);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        CryptoCurrency cryptoCurrency = (CryptoCurrency) getIntent().getSerializableExtra(EXTRA_CRYPTO_CURRENCY);
        return CryptoCurFragment.newInstance(cryptoCurrency);
    }
}
