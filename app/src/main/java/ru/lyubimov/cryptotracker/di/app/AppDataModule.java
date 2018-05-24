package ru.lyubimov.cryptotracker.di.app;

import android.content.res.AssetManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.lyubimov.cryptotracker.AssetFetcher;

@Module(includes = AppModule.class)
public class AppDataModule {

    @Provides
    @Singleton
    public AssetFetcher provideAssetFetcher(AssetManager assetManager) {
        return new AssetFetcher(assetManager);
    }

}
