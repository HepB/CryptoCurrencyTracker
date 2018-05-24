package ru.lyubimov.cryptotracker.di.app;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final Context mAppContext;

    public AppModule(@NonNull Context context) {
        this.mAppContext = context;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return mAppContext;
    }

    @Provides
    @Singleton
    public AssetManager provideAssetManager() {
        return mAppContext.getAssets();
    }

}
