package ru.lyubimov.cryptotracker.di.activity;

import dagger.Component;
import ru.lyubimov.cryptotracker.api.CoinMarketCapApi;
import ru.lyubimov.cryptotracker.api.CryptonatorApi;
import ru.lyubimov.cryptotracker.api.NintyNineCoinsApi;

@ActivityScope
@Component(modules = ActivityNetworkModule.class)
public interface ActivityComponent {

    CoinMarketCapApi getCoinMarketCapService();

    CryptonatorApi getCryptonatorApiService();

    NintyNineCoinsApi getNintyNineCoinsApi();
}
