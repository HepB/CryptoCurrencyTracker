package ru.lyubimov.cryptotracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by Alex on 28.01.2018.
 */

public class CryptoCurFragment extends Fragment {
    private static final String ARGS_CRYPTO_CURRENCY = "crypto_currency";

    private CryptoCurrency mCryptoCurrency;

    public static CryptoCurFragment newInstance(CryptoCurrency cryptoCurrency) {
        Bundle args = new Bundle();
        args.putSerializable(ARGS_CRYPTO_CURRENCY, cryptoCurrency);
        CryptoCurFragment curFragment = new CryptoCurFragment();
        curFragment.setArguments(args);
        return curFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCryptoCurrency = (CryptoCurrency) (savedInstanceState != null ? savedInstanceState.getSerializable(ARGS_CRYPTO_CURRENCY) : null);
    }
}