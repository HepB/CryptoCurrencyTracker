package ru.lyubimov.cryptotracker;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.lyubimov.cryptotracker.di.activity.DaggerActivityComponent;

import ru.lyubimov.cryptotracker.model.CryptonatorData;
import ru.lyubimov.cryptotracker.model.Market;
import ru.lyubimov.cryptotracker.model.nine.CCurrency;
import ru.lyubimov.cryptotracker.utils.ViewUtils;

/**
 * Created by Alex on 28.01.2018.
 */

public class CryptoCurFragment extends Fragment {
    private static final String TAG = "CryptoCurFragment";
    private static final String ARGS_CRYPTO_CURRENCY = "crypto_currency";

    private Unbinder unbinder;

    private AssetFetcher mAssetFetcher;

    @BindView(R.id.cur_icon) TextView mCurIco;
    @BindView(R.id.cur_name) TextView mCurName;
    @BindView(R.id.cur_cost) TextView mCurCostView;
    @BindView(R.id.btc_cost) TextView mBtcCostView;
    @BindView(R.id.day_vol) TextView mDayVolView;
    @BindView(R.id.market_cap) TextView mMarketCapView;
    @BindView(R.id.available_supply) TextView mAvailableSupVolume;
    @BindView(R.id.hour_change_vol) TextView mHourChangeVolume;
    @BindView(R.id.day_change_vol) TextView mDayChangeVolume;
    @BindView(R.id.week_change_vol) TextView mWeekChangeVolume;
    @BindView(R.id.markets_list_view) LinearLayout mMarketView;

    private CCurrency mCryptoCurrency;
    private ArrayList<Market> mMarkets;

    public static CryptoCurFragment newInstance(CCurrency cryptoCurrency) {
        Bundle args = new Bundle();
        args.putSerializable(ARGS_CRYPTO_CURRENCY, cryptoCurrency);
        CryptoCurFragment curFragment = new CryptoCurFragment();
        curFragment.setArguments(args);
        return curFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCryptoCurrency = (CCurrency) getArguments().getSerializable(ARGS_CRYPTO_CURRENCY);
        mAssetFetcher = CryptoTrackerApp.get(getActivity()).getAppComponent().getAssetFetcher();
        mMarkets = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cur_item, container, false);
        unbinder = ButterKnife.bind(this, view);
        updateUI();
        updateItems();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public class MarketsAdapter extends ArrayAdapter<Market> {

        MarketsAdapter(Context context, ArrayList<Market> markets) {
            super(context, 0, markets);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            Market market = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.market_list_item, parent, false);
            }

            TextView marketTitle = convertView.findViewById(R.id.market_title);
            TextView marketCost = convertView.findViewById(R.id.market_cost);
            TextView marketVol = convertView.findViewById(R.id.market_vol);
            marketTitle.setText(market.getMarketName());
            ViewUtils.setupCurCostView(getContext(), marketCost, market.getPrice());

            String volume = getString(R.string.volume) + " " + market.getVolume();
            marketVol.setText(volume);

            return convertView;
        }
    }

    private void updateItems() {
        String pare = mCryptoCurrency.getSymbol().toLowerCase() + "-" + "btc";
        mMarkets.clear();

        Call<CryptonatorData> call = DaggerActivityComponent.builder()
                .build()
                .getCryptonatorApiService().getMarkets(pare);
        call.enqueue(new Callback<CryptonatorData>() {
            @Override
            public void onResponse(@NonNull Call<CryptonatorData> call, @NonNull Response<CryptonatorData> response) {
                Log.i(TAG, response.message());
                CryptonatorData data = response.body();
                if(data == null) {
                    ViewUtils.showError(getCurrentFragment(), getString(R.string.parse_markets_exception));
                } else if (data.isSuccess() != null &&data.isSuccess()) {
                    List<Market> markets = data.getTicker().getMarkets();
                    mMarkets.addAll(markets != null ? markets : new ArrayList<Market>());
                } else if(data.isSuccess() == null) {
                    ViewUtils.showError(getCurrentFragment(), getString(R.string.parse_markets_exception));
                } else {
                    if(isAdded()) {
                        ViewUtils.showError(getCurrentFragment(), data.getError());
                    }
                }
                setupMarketView();
            }

            @Override
            public void onFailure(@NonNull Call<CryptonatorData> call, @NonNull Throwable t) {
                if (isAdded()) {
                    ViewUtils.showError(getCurrentFragment(), getString(R.string.parse_markets_exception));
                    setupMarketView();
                }
            }
        });
    }

    private void setupMarketView() {
        if (isAdded()) {
            mMarketView.removeAllViews();
            MarketsAdapter marketsAdapter = new MarketsAdapter(getActivity(), mMarkets);
            for (int i = 0; i < mMarkets.size(); i++) {
                View vi = marketsAdapter.getView(i, null, mMarketView);
                mMarketView.addView(vi);
            }
        }
    }

    private void updateUI() {
        ViewUtils.setCurViewIcon(getResources(), mCurIco, mAssetFetcher, mCryptoCurrency.getSymbol());
        ViewUtils.setupTitleView(mCurName, mCryptoCurrency.getName(), mCryptoCurrency.getSymbol(), null);
        ViewUtils.setupCurCostView(getContext(), mCurCostView, mCryptoCurrency.getPriceUsd());
        ViewUtils.setToBtcChangeView(getContext(), mBtcCostView, mCryptoCurrency.getPercentChangeBtc24h());
        ViewUtils.setupVolumeView(getContext(), R.string.day_volume_usd, mDayVolView, mCryptoCurrency.getVolumeUsd24h());
        ViewUtils.setupVolumeView(getContext(), R.string.market_cap_usd, mMarketCapView, mCryptoCurrency.getMarketCapUsd());
        ViewUtils.setupVolumeView(getContext(), R.string.available_supply, mAvailableSupVolume, mCryptoCurrency.getAvailableSupply());
        ViewUtils.setupChangeView(getContext(), mHourChangeVolume, mCryptoCurrency.getPercentChange1h());
        ViewUtils.setupChangeView(getContext(), mDayChangeVolume, mCryptoCurrency.getPercentChange24h());
        ViewUtils.setupChangeView(getContext(), mWeekChangeVolume, mCryptoCurrency.getPercentChange7d());
    }

    private Fragment getCurrentFragment() {
        return this;
    }
}
