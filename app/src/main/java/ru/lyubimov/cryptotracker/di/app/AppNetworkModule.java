package ru.lyubimov.cryptotracker.di.app;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.lyubimov.cryptotracker.api.CoinMarketCapApi;
import ru.lyubimov.cryptotracker.api.CryptonatorApi;

@Module
public class AppNetworkModule {

    @Singleton
    @Provides
    public CoinMarketCapApi getCoinMarketCapApi(){
        return new Retrofit.Builder()
                .baseUrl("https://api.coinmarketcap.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CoinMarketCapApi.class);
    }

    @Singleton
    @Provides
    public CryptonatorApi getCryptonatorApi() {
        return new Retrofit.Builder()
                .baseUrl("https://api.cryptonator.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CryptonatorApi.class);
    }
}
