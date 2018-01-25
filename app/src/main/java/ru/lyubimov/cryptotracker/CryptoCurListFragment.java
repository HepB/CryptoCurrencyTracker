package ru.lyubimov.cryptotracker;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Alex on 15.01.2018.
 */

public class CryptoCurListFragment extends Fragment {
    private static final String TAG = "CryptoCurListFragment";

    private RecyclerView mRecyclerView;
    private CryptoCurrencyAdapter mCryptoCurrencyAdapter;
    private List<CryptoCurrency> mCryptoCurrencies;
    private AssetFetcher mAssetFetcher;

    public static CryptoCurListFragment newInstance() {
        return new CryptoCurListFragment();
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crypto_tracker, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mCryptoCurrencyAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mCryptoCurrencyAdapter.filter(newText);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_sort_by_rank:
                mCryptoCurrencyAdapter.sortItems(CryptoCurrencyComparator.compareByRank());
                return true;
            case R.id.menu_item_sort_by_volume:
                mCryptoCurrencyAdapter.sortItems(CryptoCurrencyComparator.compareByVolume());
                return true;
            case R.id.menu_item_sort_by_cost:
                mCryptoCurrencyAdapter.sortItems(CryptoCurrencyComparator.compareByCost());
                return true;
            case R.id.menu_item_sort_by_rise:
                mCryptoCurrencyAdapter.sortItems(CryptoCurrencyComparator.compareByRise());
                return true;
            case R.id.menu_item_sort_by_fall:
                mCryptoCurrencyAdapter.sortItems(CryptoCurrencyComparator.compareByFallingDown());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        TextView mMaxSupplyVol;

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
                mMaxSupplyVol = itemView.findViewById(R.id.total_supply_vol);
            }
        }

        @SuppressLint("SetTextI18n")
        public void bind(CryptoCurrency cryptoCurrency) {
            mCryptoCurrency = cryptoCurrency;

            mCurIco.setImageDrawable(mAssetFetcher.getDrawableFromAssets(mCryptoCurrency.getSymbol()));
            mCurName.setText(mCryptoCurrency.getName().toUpperCase());

            mCurCost.setText(mCryptoCurrency.getPriceCur());
            mBtcCost.setText(mCryptoCurrency.getPriceBtc());
            mCurVolume.setText(mCryptoCurrency.getDayVolumeCur());

            setupChangeView(mOneHourChange, mCryptoCurrency.getHourPercentChange());
            setupChangeView(mOneDayChange, mCryptoCurrency.getDayPercentChange());
            setupChangeView(mOneWeekChange, mCryptoCurrency.getWeekPercentChange());

            if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mMarketCapVol.setText(mCryptoCurrency.getMarketCapCur());
                mAvailableSupplyVol.setText(mCryptoCurrency.getAvailableSupply());
                mMaxSupplyVol.setText(mCryptoCurrency.getMaxSupply());
            }
        }

        private void setupChangeView(TextView textView, String param) {
            if(param != null) {
                Double numParam = Double.valueOf(param);
                String textToView;
                if(numParam >= 0) {
                    textView.setTextColor(getResources().getColor(R.color.colorGreen));
                    textToView = "+" + param + "%";
                    textView.setText(textToView);
                } else {
                    textView.setTextColor(getResources().getColor(R.color.colorRed));
                    textToView = param + "%";
                    textView.setText(textToView);
                }
            } else {
                textView.setText("-");
                textView.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        }
    }

    private class CryptoCurrencyAdapter extends RecyclerView.Adapter<CryptoCurrencyHolder> {
        private List<CryptoCurrency> mCryptoCurrencies;
        private List<CryptoCurrency> mCryptoCurrenciesCopy;

        public CryptoCurrencyAdapter(List<CryptoCurrency> cryptoCurrencies) {
            mCryptoCurrencies = cryptoCurrencies;
            mCryptoCurrenciesCopy = new ArrayList<>();
            mCryptoCurrenciesCopy.addAll(cryptoCurrencies);
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

        public void filter(String text) {
            mCryptoCurrencies.clear();
            if(text.isEmpty()){
                mCryptoCurrencies.addAll(mCryptoCurrenciesCopy);
            } else{
                for(CryptoCurrency item: mCryptoCurrenciesCopy){
                    if(item.getName().toLowerCase().contains(text.toLowerCase()) || item.getSymbol().toLowerCase().contains(text.toLowerCase())){
                        mCryptoCurrencies.add(item);
                    }
                }
            }
            notifyDataSetChanged();
        }

        public void sortItems(Comparator<CryptoCurrency> comparator) {
            Collections.sort(mCryptoCurrencies, comparator);
            notifyDataSetChanged();
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
            mCryptoCurrencies = aCryptoCurrencies;
            setupAdapter();
        }
    }

    private void setupAdapter() {
        if (isAdded()) {
            mCryptoCurrencyAdapter = new CryptoCurrencyAdapter(mCryptoCurrencies);
            mRecyclerView.setAdapter(mCryptoCurrencyAdapter);
        }
    }

    private void updateItems() {
        new FetchCurrenciesTask().execute();
    }
}
