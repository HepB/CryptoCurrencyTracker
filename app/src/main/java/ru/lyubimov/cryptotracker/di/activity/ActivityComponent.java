package ru.lyubimov.cryptotracker.di.activity;

import dagger.Component;
import ru.lyubimov.cryptotracker.api.CryptonatorApi;
import ru.lyubimov.cryptotracker.api.NintyNineCoinsApi;

@ActivityScope
@Component(modules = ActivityNetworkModule.class)
public interface ActivityComponent {

    CryptonatorApi getCryptonatorApiService();

    NintyNineCoinsApi getNintyNineCoinsApi();
}
