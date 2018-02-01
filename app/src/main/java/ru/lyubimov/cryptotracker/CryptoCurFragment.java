package ru.lyubimov.cryptotracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Alex on 28.01.2018.
 */

public class CryptoCurFragment extends Fragment {
    private static final String ARGS_CRYPTO_CURRENCY = "crypto_currency";

    private TextView mHourChangeVolume;
    private TextView mDayChangeVolume;
    private TextView mWeekChangeVolume;


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
        mCryptoCurrency = (CryptoCurrency) getArguments().getSerializable(ARGS_CRYPTO_CURRENCY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cur_item, container, false);

        mHourChangeVolume = view.findViewById(R.id.hour_change_vol);
        ViewTextUtils.setupChangeView(getResources(), mHourChangeVolume, mCryptoCurrency.getHourPercentChange());

        mDayChangeVolume = view.findViewById(R.id.day_change_vol);
        ViewTextUtils.setupChangeView(getResources(), mDayChangeVolume, mCryptoCurrency.getDayPercentChange());

        mWeekChangeVolume = view.findViewById(R.id.week_change_vol);
        ViewTextUtils.setupChangeView(getResources(), mWeekChangeVolume, mCryptoCurrency.getWeekPercentChange());

        return view;
    }
}
