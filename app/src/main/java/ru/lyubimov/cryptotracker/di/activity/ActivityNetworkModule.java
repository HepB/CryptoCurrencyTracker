package ru.lyubimov.cryptotracker.di.activity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.lyubimov.cryptotracker.api.CoinMarketCapApi;
import ru.lyubimov.cryptotracker.api.CryptonatorApi;

@Module
public class ActivityNetworkModule {
    @ActivityScope
    @Provides
    public CoinMarketCapApi getCoinMarketCapApi(){
        return new Retrofit.Builder()
                .baseUrl("https://api.coinmarketcap.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CoinMarketCapApi.class);
    }

    @ActivityScope
    @Provides
    public CryptonatorApi getCryptonatorApi() {
        return new Retrofit.Builder()
                .baseUrl("https://api.cryptonator.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CryptonatorApi.class);
    }
}
