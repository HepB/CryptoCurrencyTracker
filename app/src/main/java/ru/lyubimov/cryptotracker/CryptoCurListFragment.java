package ru.lyubimov.cryptotracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.lyubimov.cryptotracker.model.CryptoCurrency;

/**
 * Created by Alex on 15.01.2018.
 */

public class CryptoCurListFragment extends Fragment {
    private static final String TAG = "CryptoCurListFragment";

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Spinner mSortSpinner;

    private CryptoCurrencyAdapter mCryptoCurrencyAdapter;
    private SearchView mSearchView;
    private List<CryptoCurrency> mCryptoCurrencies;
    private AssetFetcher mAssetFetcher;

    public static CryptoCurListFragment newInstance() {
        return new CryptoCurListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mAssetFetcher = new AssetFetcher(getContext().getAssets());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crypto_cur_list, container, false);
        mRecyclerView = view.findViewById(R.id.currency_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSortSpinner = view.findViewById(R.id.sort_spinner);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateItems();
            }

        });
        updateItems();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crypto_tracker, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.menu_item_search);
        mSearchView = (SearchView) searchMenuItem.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!mSwipeRefreshLayout.isRefreshing()) {
                    StoredPreferences.setStoredQuery(getActivity(), query);
                    mCryptoCurrencyAdapter.filter(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!mSwipeRefreshLayout.isRefreshing()) {
                    StoredPreferences.setStoredQuery(getActivity(), newText);
                    mCryptoCurrencyAdapter.filter(newText);
                }
                return false;
            }
        });

        mSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = StoredPreferences.getStoredQuery(getActivity());
                mSearchView.setQuery(query, true);
            }
        });

        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                StoredPreferences.setStoredQuery(getActivity(), "");
                return false;
            }
        });
    }

    private class CryptoCurrencyHolder extends RecyclerView.ViewHolder implements ViewGroup.OnClickListener {
        CryptoCurrency mCryptoCurrency;

        TextView mCurIco;
        TextView mCurName;
        TextView mCurCost;
        TextView mBtcCost;
        TextView mDayVolume;
        TextView mOneHourChange;
        TextView mOneDayChange;
        TextView mOneWeekChange;

        CryptoCurrencyHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_ccurrency, parent, false));
            mCurIco = itemView.findViewById(R.id.cur_icon);
            mCurName = itemView.findViewById(R.id.cur_name);
            mCurCost = itemView.findViewById(R.id.cur_cost);
            mBtcCost = itemView.findViewById(R.id.btc_cost);
            mDayVolume = itemView.findViewById(R.id.usd_day_vol);
            mOneHourChange = itemView.findViewById(R.id.hour_change_volume);
            mOneDayChange = itemView.findViewById(R.id.day_change_volume);
            mOneWeekChange = itemView.findViewById(R.id.week_change_volume);
            itemView.setOnClickListener(this);
        }

        @SuppressLint("SetTextI18n")
        void bind(CryptoCurrency cryptoCurrency, int position) {
            mCryptoCurrency = cryptoCurrency;

            ViewUtils.setupTitleView(mCurName, mCryptoCurrency.getName(), null, position + 1);
            ViewUtils.setCurViewIcon(getResources(), mCurIco, mAssetFetcher, mCryptoCurrency.getSymbol());
            ViewUtils.setupCurCostView(getResources(), mCurCost, mCryptoCurrency.getPriceCur());
            ViewUtils.setupBtcCostView(getResources(), mBtcCost, mCryptoCurrency.getPriceBtc());
            ViewUtils.setupVolumeView(getResources(), R.string.day_volume_usd, mDayVolume, mCryptoCurrency.getDayVolumeCur());
            ViewUtils.setupChangeView(getResources(), mOneHourChange, mCryptoCurrency.getHourPercentChange());
            ViewUtils.setupChangeView(getResources(), mOneDayChange, mCryptoCurrency.getDayPercentChange());
            ViewUtils.setupChangeView(getResources(), mOneWeekChange, mCryptoCurrency.getWeekPercentChange());
        }

        @Override
        public void onClick(View v) {
            Intent intent = CryptoCurItemActivity.newIntent(getContext(), mCryptoCurrency);
            startActivity(intent);
        }
    }

    private class CryptoCurrencyAdapter extends RecyclerView.Adapter<CryptoCurrencyHolder> {
        private List<CryptoCurrency> mCryptoCurrencies;
        private List<CryptoCurrency> mCryptoCurrenciesCopy;

        CryptoCurrencyAdapter(List<CryptoCurrency> cryptoCurrencies) {
            mCryptoCurrencies = cryptoCurrencies;
            mCryptoCurrenciesCopy = new ArrayList<>();
            mCryptoCurrenciesCopy.addAll(cryptoCurrencies);

            // compareType - индекс в массиве sort_types
            int compareType = StoredPreferences.getStoredSort(getActivity());
            if (compareType == 0) {
                sortItems(CryptoCurrencyComparator.compareByRank());
            } else if (compareType == 1) {
                sortItems(CryptoCurrencyComparator.compareByVolume());
            } else if (compareType == 2) {
                sortItems(CryptoCurrencyComparator.compareByCost());
            } else if (compareType == 3) {
                sortItems(CryptoCurrencyComparator.compareByHourRise());
            } else if (compareType == 4) {
                sortItems(CryptoCurrencyComparator.compareByHourFallingDown());
            } else if (compareType == 5) {
                sortItems(CryptoCurrencyComparator.compareByDayRise());
            } else if (compareType == 6) {
                sortItems(CryptoCurrencyComparator.compareByDayFallingDown());
            } else if (compareType == 7) {
                sortItems(CryptoCurrencyComparator.compareByWeekRise());
            } else if (compareType == 8) {
                sortItems(CryptoCurrencyComparator.compareByWeekFallingDown());
            }
            filter(StoredPreferences.getStoredQuery(getActivity()));
        }

        @Override
        public CryptoCurrencyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new CryptoCurrencyHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(CryptoCurrencyHolder holder, int position) {
            CryptoCurrency cryptoCurrency = mCryptoCurrencies.get(position);
            holder.bind(cryptoCurrency, position);
        }

        @Override
        public int getItemCount() {
            return mCryptoCurrencies.size();
        }

        void filter(String text) {
            mCryptoCurrencies.clear();
            if (text == null || text.isEmpty()) {
                mCryptoCurrencies.addAll(mCryptoCurrenciesCopy);
            } else {
                for (CryptoCurrency item : mCryptoCurrenciesCopy) {
                    if (item.getName().toLowerCase().contains(text.toLowerCase()) || item.getSymbol().toLowerCase().contains(text.toLowerCase())) {
                        mCryptoCurrencies.add(item);
                    }
                }
            }
            notifyDataSetChanged();
        }

        void sortItems(Comparator<CryptoCurrency> comparator) {
            Collections.sort(mCryptoCurrencies, comparator);
            Collections.sort(mCryptoCurrenciesCopy, comparator);
            notifyDataSetChanged();
        }
    }

    private void setupAdapter() {
        if (isAdded()) {
            mCryptoCurrencyAdapter = new CryptoCurrencyAdapter(mCryptoCurrencies);
            mRecyclerView.setAdapter(mCryptoCurrencyAdapter);
            setupSortSpinner();
        }
    }

    private void updateItems() {
        if(mCryptoCurrencies == null) {
            mCryptoCurrencies = new ArrayList<>();
        }
        mSwipeRefreshLayout.setRefreshing(true);
        Call<List<CryptoCurrency>> call = App.getCoinMarketCapApi().getCryptoCurrencies(0, null);
        call.enqueue(new Callback<List<CryptoCurrency>>() {
            @Override
            public void onResponse(@NonNull Call<List<CryptoCurrency>> call, @NonNull Response<List<CryptoCurrency>> response) {
                mSwipeRefreshLayout.setRefreshing(false);
                List<CryptoCurrency> result = response.body();
                if (result != null) {
                    mCryptoCurrencies.clear();
                    mCryptoCurrencies.addAll(result);
                } else {
                    mCryptoCurrencies = new ArrayList<>();
                }
                onItemsLoadComplete();
                setupAdapter();
            }

            @Override
            public void onFailure(@NonNull Call<List<CryptoCurrency>> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                onItemsLoadComplete();
                setupAdapter();
            }
        });
    }

    private void onItemsLoadComplete() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void setupSortSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.sort_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSortSpinner.setAdapter(adapter);

        mSortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mCryptoCurrencyAdapter.sortItems(CryptoCurrencyComparator.compareByRank());
                        StoredPreferences.setStoredSort(getActivity(), position);
                        break;
                    case 1:
                        mCryptoCurrencyAdapter.sortItems(CryptoCurrencyComparator.compareByVolume());
                        StoredPreferences.setStoredSort(getActivity(), position);
                        break;
                    case 2:
                        mCryptoCurrencyAdapter.sortItems(CryptoCurrencyComparator.compareByCost());
                        StoredPreferences.setStoredSort(getActivity(), position);
                        break;
                    case 3:
                        mCryptoCurrencyAdapter.sortItems(CryptoCurrencyComparator.compareByHourRise());
                        StoredPreferences.setStoredSort(getActivity(), position);
                        break;
                    case 4:
                        mCryptoCurrencyAdapter.sortItems(CryptoCurrencyComparator.compareByHourFallingDown());
                        StoredPreferences.setStoredSort(getActivity(), position);
                        break;
                    case 5:
                        mCryptoCurrencyAdapter.sortItems(CryptoCurrencyComparator.compareByDayRise());
                        StoredPreferences.setStoredSort(getActivity(), position);
                        break;
                    case 6:
                        mCryptoCurrencyAdapter.sortItems(CryptoCurrencyComparator.compareByDayFallingDown());
                        StoredPreferences.setStoredSort(getActivity(), position);
                        break;
                    case 7:
                        mCryptoCurrencyAdapter.sortItems(CryptoCurrencyComparator.compareByWeekRise());
                        StoredPreferences.setStoredSort(getActivity(), position);
                        break;
                    case 8:
                        mCryptoCurrencyAdapter.sortItems(CryptoCurrencyComparator.compareByWeekFallingDown());
                        StoredPreferences.setStoredSort(getActivity(), position);
                        break;
                    default:
                        //что-нибудь сюда
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mSortSpinner.setSelection(StoredPreferences.getStoredSort(getActivity()));
        adapter.notifyDataSetChanged();
    }
}
