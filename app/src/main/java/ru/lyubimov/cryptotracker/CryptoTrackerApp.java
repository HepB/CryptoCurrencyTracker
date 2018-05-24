package ru.lyubimov.cryptotracker;

import android.app.Activity;
import android.app.Application;

import ru.lyubimov.cryptotracker.di.app.AppComponent;
import ru.lyubimov.cryptotracker.di.app.AppModule;
import ru.lyubimov.cryptotracker.di.app.DaggerAppComponent;

public class CryptoTrackerApp extends Application {

    private AppComponent mAppComponent;

    public static CryptoTrackerApp get(Activity activity) {
        return (CryptoTrackerApp) activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
