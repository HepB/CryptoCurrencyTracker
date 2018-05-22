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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.lyubimov.cryptotracker.di.app.DaggerAppComponent;
import ru.lyubimov.cryptotracker.model.CryptoCurrency;
import ru.lyubimov.cryptotracker.model.CryptonatorData;
import ru.lyubimov.cryptotracker.model.Market;

/**
 * Created by Alex on 28.01.2018.
 */

public class CryptoCurFragment extends Fragment {
    private static final String TAG = "CryptoCurFragment";
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
    private LinearLayout mMarketView;
    private Spinner mCurrTypeSpinner;

    private CryptoCurrency mCryptoCurrency;
    private ArrayList<Market> mMarkets;

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
        mMarkets = new ArrayList<>();
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
        mCurrTypeSpinner = view.findViewById(R.id.cur_type_spinner);
        mMarketView = view.findViewById(R.id.markets_list_view);

        updateUI();
        updateItems();
        return view;
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

            if (mCurrTypeSpinner.getSelectedItemPosition() == 0) {
                ViewUtils.setupCurCostView(getResources(), marketCost, market.getPrice());
            } else {
                ViewUtils.setupBtcCostView(getResources(), marketCost, market.getPrice());
            }

            String volume = getString(R.string.volume) + " " + market.getVolume();
            marketVol.setText(volume);

            return convertView;
        }
    }

    private void updateItems() {
        String pare = mCryptoCurrency.getSymbol().toLowerCase()
                + "-"
                + mCurrTypeSpinner.getSelectedItem().toString().toLowerCase();
        mMarkets.clear();

        Call<CryptonatorData> call = DaggerAppComponent.builder()
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

    private void setupCurrSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.cur_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCurrTypeSpinner.setAdapter(adapter);

        mCurrTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateItems();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
        setupCurrSpinner();
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
    }

    private Fragment getCurrentFragment() {
        return this;
    }
}
