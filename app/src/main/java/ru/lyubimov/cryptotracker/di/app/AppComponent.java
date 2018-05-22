package ru.lyubimov.cryptotracker.di.app;

import javax.inject.Singleton;

import dagger.Component;
import ru.lyubimov.cryptotracker.api.CoinMarketCapApi;
import ru.lyubimov.cryptotracker.api.CryptonatorApi;

@Singleton
@Component(modules = AppNetworkModule.class)
public interface AppComponent {

    CoinMarketCapApi getCoinMarketCapService();

    CryptonatorApi getCryptonatorApiService();
}
