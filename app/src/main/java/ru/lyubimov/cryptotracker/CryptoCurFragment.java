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

    private AssetFetcher mAssetFetcher;

    private TextView mCurIco;
    private TextView mCurName;
    private TextView mCurCostView;
    private TextView mBtcCostView;
    private TextView mDayVolView;
    private TextView mMarketCapView;
    private TextView mAvailableSupVolume;
    private TextView mMaxSupVolume;
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
        mAssetFetcher = new AssetFetcher(getContext().getAssets());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cur_item, container, false);

        mCurIco = view.findViewById(R.id.cur_icon);
        mCurName = view.findViewById(R.id.cur_name);
        mCurCostView = view.findViewById(R.id.cur_cost);
        mBtcCostView = view.findViewById(R.id.btc_cost);
        mDayVolView = view.findViewById(R.id.day_vol);
        mMarketCapView = view.findViewById(R.id.market_cap);
        mAvailableSupVolume = view.findViewById(R.id.available_supply);
        mMaxSupVolume = view.findViewById(R.id.max_supply);
        mHourChangeVolume = view.findViewById(R.id.hour_change_vol);
        mDayChangeVolume = view.findViewById(R.id.day_change_vol);
        mWeekChangeVolume = view.findViewById(R.id.week_change_vol);

        ViewUtils.setCurViewIcon(getResources(), mCurIco, mAssetFetcher, mCryptoCurrency.getSymbol());

        ViewUtils.setupTitleView(mCurName, mCryptoCurrency.getName(), mCryptoCurrency.getSymbol(), null);
        ViewUtils.setupCurCostView(getResources(), mCurCostView, mCryptoCurrency.getPriceCur());
        ViewUtils.setupBtcCostView(getResources(), mBtcCostView, mCryptoCurrency.getPriceBtc());

        ViewUtils.setupVolumeView(getResources(), R.string.day_volume_usd, mDayVolView, mCryptoCurrency.getDayVolumeCur());
        ViewUtils.setupVolumeView(getResources(), R.string.market_cap_usd, mMarketCapView, mCryptoCurrency.getMarketCapCur());
        ViewUtils.setupVolumeView(getResources(), R.string.available_supply, mAvailableSupVolume, mCryptoCurrency.getAvailableSupply());
        ViewUtils.setupVolumeView(getResources(), R.string.max_supply, mMaxSupVolume, mCryptoCurrency.getMaxSupply());

        ViewUtils.setupChangeView(getResources(), mHourChangeVolume, mCryptoCurrency.getHourPercentChange());
        ViewUtils.setupChangeView(getResources(), mDayChangeVolume, mCryptoCurrency.getDayPercentChange());
        ViewUtils.setupChangeView(getResources(), mWeekChangeVolume, mCryptoCurrency.getWeekPercentChange());

        return view;
    }
}
