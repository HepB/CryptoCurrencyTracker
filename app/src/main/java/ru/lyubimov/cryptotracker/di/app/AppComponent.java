package ru.lyubimov.cryptotracker.di.app;

import javax.inject.Singleton;

import dagger.Component;
import ru.lyubimov.cryptotracker.AssetFetcher;

@Singleton
@Component(modules = {AppDataModule.class, AppModule.class})
public interface AppComponent {

    AssetFetcher getAssetFetcher();
}
