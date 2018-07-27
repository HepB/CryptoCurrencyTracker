package ru.lyubimov.cryptotracker;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.lyubimov.cryptotracker.di.activity.DaggerActivityComponent;
import ru.lyubimov.cryptotracker.model.nine.CryptoCurrency;
import ru.lyubimov.cryptotracker.model.nine.NintyNineCoinsData;
import ru.lyubimov.cryptotracker.utils.ComparatorUtils;
import ru.lyubimov.cryptotracker.utils.StoredPreferencesUtils;
import ru.lyubimov.cryptotracker.utils.ViewUtils;

/**
 * Created by Alex on 15.01.2018.
 */

public class CryptoCurListFragment extends Fragment {
    private static final String TAG = "CryptoCurListFragment";

    private Unbinder unbinder;

    @BindView(R.id.currency_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.sort_spinner) Spinner mSortSpinner;

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
        mAssetFetcher = CryptoTrackerApp.get(getActivity()).getAppComponent().getAssetFetcher();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crypto_cur_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
                    StoredPreferencesUtils.setStoredQuery(getActivity(), query);
                    mCryptoCurrencyAdapter.filter(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!mSwipeRefreshLayout.isRefreshing()) {
                    StoredPreferencesUtils.setStoredQuery(getActivity(), newText);
                    mCryptoCurrencyAdapter.filter(newText);
                }
                return false;
            }
        });

        mSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = StoredPreferencesUtils.getStoredQuery(getActivity());
                mSearchView.setQuery(query, true);
            }
        });

        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                StoredPreferencesUtils.setStoredQuery(getActivity(), "");
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    class CryptoCurrencyHolder extends RecyclerView.ViewHolder{
        CryptoCurrency mCryptoCurrency;

        @BindView(R.id.cur_icon) TextView mCurIco;
        @BindView(R.id.cur_name) TextView mCurName;
        @BindView(R.id.cur_cost) TextView mCurCost;
        @BindView(R.id.change_to_btc) TextView changeToBtc;
        @BindView(R.id.usd_day_vol) TextView mDayVolume;
        @BindView(R.id.hour_change_volume) TextView mOneHourChange;
        @BindView(R.id.day_change_volume) TextView mOneDayChange;
        @BindView(R.id.week_change_volume) TextView mOneWeekChange;

        CryptoCurrencyHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_ccurrency, parent, false));
            ButterKnife.bind(this, itemView);
        }

        @SuppressLint("SetTextI18n")
        void bind(CryptoCurrency cryptoCurrency, int position) {
            mCryptoCurrency = cryptoCurrency;

            ViewUtils.setupTitleView(mCurName, mCryptoCurrency.getName(),null, position + 1);
            ViewUtils.setCurViewIcon(getResources(), mCurIco, mAssetFetcher, mCryptoCurrency.getSymbol());
            ViewUtils.setupCurCostView(getContext(), mCurCost, mCryptoCurrency.getPriceUsd());
            ViewUtils.setToBtcChangeView(getContext(), changeToBtc, mCryptoCurrency.getPercentChangeBtc24h());
            ViewUtils.setupVolumeView(getContext(), R.string.day_volume_usd, mDayVolume, mCryptoCurrency.getVolumeUsd24h());
            ViewUtils.setupChangeView(getContext(), mOneHourChange, mCryptoCurrency.getPercentChange1h());
            ViewUtils.setupChangeView(getContext(), mOneDayChange, mCryptoCurrency.getPercentChange24h());
            ViewUtils.setupChangeView(getContext(), mOneWeekChange, mCryptoCurrency.getPercentChange7d());
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
            int compareType = StoredPreferencesUtils.getStoredSort(getActivity());
            if (compareType == 0) {
                sortItems(ComparatorUtils.compareByRank());
            } else if (compareType == 1) {
                sortItems(ComparatorUtils.compareByVolume());
            } else if (compareType == 2) {
                sortItems(ComparatorUtils.compareByCost());
            } else if (compareType == 3) {
                sortItems(ComparatorUtils.compareByHourRise());
            } else if (compareType == 4) {
                sortItems(ComparatorUtils.compareByHourFallingDown());
            } else if (compareType == 5) {
                sortItems(ComparatorUtils.compareByDayRise());
            } else if (compareType == 6) {
                sortItems(ComparatorUtils.compareByDayFallingDown());
            } else if (compareType == 7) {
                sortItems(ComparatorUtils.compareByWeekRise());
            } else if (compareType == 8) {
                sortItems(ComparatorUtils.compareByWeekFallingDown());
            }
            filter(StoredPreferencesUtils.getStoredQuery(getActivity()));
        }

        @NonNull
        @Override
        public CryptoCurrencyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new CryptoCurrencyHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CryptoCurrencyHolder holder, int position) {
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
        Call<NintyNineCoinsData> call = DaggerActivityComponent
                .builder()
                .build()
                .getNintyNineCoinsApi()
                .getCryptoCurrencies();
        call.enqueue(new Callback<NintyNineCoinsData>() {
            @Override
            public void onResponse(@NonNull Call<NintyNineCoinsData> call, @NonNull Response<NintyNineCoinsData> response) {
                mSwipeRefreshLayout.setRefreshing(false);
                NintyNineCoinsData result = response.body();
                if (result != null && result.getStatus().equals("true")) {
                    mCryptoCurrencies.clear();
                    mCryptoCurrencies.addAll(result.getResult());
                } else {
                    ViewUtils.showError(getCurrentFragment(), getString(R.string.parse_crypto_cur_exception));
                    mCryptoCurrencies = new ArrayList<>();
                }
                onItemsLoadComplete();
                setupAdapter();
            }

            @Override
            public void onFailure(@NonNull Call<NintyNineCoinsData> call, @NonNull Throwable t) {
                ViewUtils.showError(getCurrentFragment(), getString(R.string.parse_crypto_cur_exception));
                Log.e(TAG, t.getMessage());
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
                        mCryptoCurrencyAdapter.sortItems(ComparatorUtils.compareByRank());
                        StoredPreferencesUtils.setStoredSort(getActivity(), position);
                        break;
                    case 1:
                        mCryptoCurrencyAdapter.sortItems(ComparatorUtils.compareByVolume());
                        StoredPreferencesUtils.setStoredSort(getActivity(), position);
                        break;
                    case 2:
                        mCryptoCurrencyAdapter.sortItems(ComparatorUtils.compareByCost());
                        StoredPreferencesUtils.setStoredSort(getActivity(), position);
                        break;
                    case 3:
                        mCryptoCurrencyAdapter.sortItems(ComparatorUtils.compareByHourRise());
                        StoredPreferencesUtils.setStoredSort(getActivity(), position);
                        break;
                    case 4:
                        mCryptoCurrencyAdapter.sortItems(ComparatorUtils.compareByHourFallingDown());
                        StoredPreferencesUtils.setStoredSort(getActivity(), position);
                        break;
                    case 5:
                        mCryptoCurrencyAdapter.sortItems(ComparatorUtils.compareByDayRise());
                        StoredPreferencesUtils.setStoredSort(getActivity(), position);
                        break;
                    case 6:
                        mCryptoCurrencyAdapter.sortItems(ComparatorUtils.compareByDayFallingDown());
                        StoredPreferencesUtils.setStoredSort(getActivity(), position);
                        break;
                    case 7:
                        mCryptoCurrencyAdapter.sortItems(ComparatorUtils.compareByWeekRise());
                        StoredPreferencesUtils.setStoredSort(getActivity(), position);
                        break;
                    case 8:
                        mCryptoCurrencyAdapter.sortItems(ComparatorUtils.compareByWeekFallingDown());
                        StoredPreferencesUtils.setStoredSort(getActivity(), position);
                        break;
                    default:
                        //что-нибудь сюда
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mSortSpinner.setSelection(StoredPreferencesUtils.getStoredSort(getActivity()));
        adapter.notifyDataSetChanged();
    }

    private Fragment getCurrentFragment() {
        return this;
    }
}
