package ru.lyubimov.cryptotracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Alex on 15.01.2018.
 */

public class CryptoCurrenciesFragment extends Fragment {
    private static final String TAG = "CryptoCurrenciesFragment";

    private RecyclerView mRecyclerView;

    public static CryptoCurrenciesFragment newInstance() {
        return new CryptoCurrenciesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crypto_tracker, container, false);
        mRecyclerView = view.findViewById(R.id.currency_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
}
