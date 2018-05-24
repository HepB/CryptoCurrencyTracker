package ru.lyubimov.cryptotracker.di.activity;

import javax.inject.Singleton;

import dagger.Component;
import ru.lyubimov.cryptotracker.api.CoinMarketCapApi;
import ru.lyubimov.cryptotracker.api.CryptonatorApi;

@ActivityScope
@Component(modules = ActivityNetworkModule.class)
public interface ActivityComponent {

    CoinMarketCapApi getCoinMarketCapService();

    CryptonatorApi getCryptonatorApiService();
}
