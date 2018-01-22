package ru.lyubimov.cryptotracker;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Alex on 15.01.2018.
 */

public class CryptoCurrenciesFragment extends Fragment {
    private static final String TAG = "CryptoCurrenciesFragment";

    private RecyclerView mRecyclerView;
    List<CryptoCurrency> mCryptoCurrencies;
    AssetFetcher mAssetFetcher;

    public static CryptoCurrenciesFragment newInstance() {
        return new CryptoCurrenciesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        updateItems();
        setHasOptionsMenu(true);
        mAssetFetcher = new AssetFetcher(getContext().getAssets());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crypto_tracker, container, false);
        mRecyclerView = view.findViewById(R.id.currency_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateItems();
    }

    //TODO: убрать костыль с orientation
    private class CryptoCurrencyHolder extends RecyclerView.ViewHolder {
        CryptoCurrency mCryptoCurrency;

        ImageView mCurIco;
        TextView mCurName;
        TextView mCurCost;
        TextView mBtcCost;
        TextView mCurVolume;
        TextView mOneHourChange;
        TextView mOneDayChange;
        TextView mOneWeekChange;
        TextView mMarketCapVol;
        TextView mAvailableSupplyVol;
        TextView mTotalSupplyVol;

        public CryptoCurrencyHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_ccurrency, parent, false));
            mCurIco = itemView.findViewById(R.id.cur_icon);
            mCurName = itemView.findViewById(R.id.cur_name);
            mCurCost = itemView.findViewById(R.id.cur_cost_val);
            mBtcCost = itemView.findViewById(R.id.btc_cost_val);
            mCurVolume = itemView.findViewById(R.id.usd_vol_val);
            mOneHourChange = itemView.findViewById(R.id.hour_change_volume);
            mOneDayChange = itemView.findViewById(R.id.day_change_volume);
            mOneWeekChange = itemView.findViewById(R.id.week_change_volume);
            if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mMarketCapVol = itemView.findViewById(R.id.market_cap_vol);
                mAvailableSupplyVol = itemView.findViewById(R.id.available_supply_vol);
                mTotalSupplyVol = itemView.findViewById(R.id.total_supply_vol);
            }
        }

        @SuppressLint("SetTextI18n")
        public void bind(CryptoCurrency cryptoCurrency) {
            getActivity().getRequestedOrientation();
            mCryptoCurrency = cryptoCurrency;
            mCurIco.setImageDrawable(mAssetFetcher.getDrawableFromAssets(mCryptoCurrency.getSymbol()));
            mCurName.setText(mCryptoCurrency.getName().toUpperCase());
            mCurCost.setText(mCryptoCurrency.getPriceCur());
            mBtcCost.setText(mCryptoCurrency.getPriceBtc());
            mCurVolume.setText(mCryptoCurrency.getDayVolumeCur());
            mOneHourChange.setText(mCryptoCurrency.getHourPercentChange());
            mOneDayChange.setText(mCryptoCurrency.getDayPercentChange());
            mOneWeekChange.setText(mCryptoCurrency.getWeekPercentChange());
            if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mMarketCapVol.setText(mCryptoCurrency.getMarketCapCur());
                mAvailableSupplyVol.setText(mCryptoCurrency.getAvailableSupply());
                mTotalSupplyVol.setText(mCryptoCurrency.getTotalSupply());
            }

        }
    }

    private class CryptoCurrencyAdapter extends RecyclerView.Adapter<CryptoCurrencyHolder> {
        List<CryptoCurrency> mCryptoCurrencies;

        public CryptoCurrencyAdapter(List<CryptoCurrency> cryptoCurrencies) {
            mCryptoCurrencies = cryptoCurrencies;
        }

        @Override
        public CryptoCurrencyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new CryptoCurrencyHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(CryptoCurrencyHolder holder, int position) {
            CryptoCurrency cryptoCurrency = mCryptoCurrencies.get(position);
            holder.bind(cryptoCurrency);

        }

        @Override
        public int getItemCount() {
            return mCryptoCurrencies.size();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class FetchCurrenciesTask extends AsyncTask<Void, Void, List<CryptoCurrency>> {

        @Override
        protected List<CryptoCurrency> doInBackground(Void... voids) {
            return new CoinMarketCapFetcher().fetchCryptoCurrencies();
        }

        @Override
        protected void onPostExecute(List<CryptoCurrency> aCryptoCurrencies) {
            //super.onPostExecute(aCryptoCurrencies);
            mCryptoCurrencies = aCryptoCurrencies;
            setupAdapter();
        }
    }

    private void setupAdapter() {
        if (isAdded()) {
            mRecyclerView.setAdapter(new CryptoCurrencyAdapter(mCryptoCurrencies));
        }
    }

    private void updateItems() {
        new FetchCurrenciesTask().execute();
    }
}
