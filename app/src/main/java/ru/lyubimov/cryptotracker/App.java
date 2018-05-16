package ru.lyubimov.cryptotracker;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.lyubimov.cryptotracker.api.CoinMarketCapApi;
import ru.lyubimov.cryptotracker.api.CryptonatorApi;

/**
 * Created by Alex on 10.03.2018.
 */

public class App extends Application {

    private static CoinMarketCapApi sCoinMarketCapApi;
    private static CryptonatorApi sCryptonatorApi;

    @Override
    public void onCreate() {
        super.onCreate();

        sCoinMarketCapApi = new Retrofit.Builder()
                .baseUrl("https://api.coinmarketcap.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CoinMarketCapApi.class);

        sCryptonatorApi = new Retrofit.Builder()
                .baseUrl("https://api.cryptonator.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CryptonatorApi.class);
    }

    public static CoinMarketCapApi getCoinMarketCapApi() {
        return sCoinMarketCapApi;
    }

    public static CryptonatorApi getCryptonatorApi() {
        return sCryptonatorApi;
    }
}
