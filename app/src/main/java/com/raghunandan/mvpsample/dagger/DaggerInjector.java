package com.raghunandan.mvpsample.dagger;

import com.raghunandan.mvpsample.dagger.components.AppComponent;

import com.raghunandan.mvpsample.dagger.components.DaggerAppComponent;
import com.raghunandan.mvpsample.dagger.components.DaggerNetworkComponent;
import com.raghunandan.mvpsample.dagger.components.NetworkComponent;
import com.raghunandan.mvpsample.dagger.modules.AppModule;
import com.raghunandan.mvpsample.dagger.modules.NetworkModule;

public class DaggerInjector {
    private static AppComponent appComponent = DaggerAppComponent.builder()
             .appModule(new AppModule())
             .build();

    public static AppComponent get() {
        return appComponent;
    }

    private static NetworkComponent netComponent = DaggerNetworkComponent.builder()

            .networkModule(new NetworkModule())
            .build();

    public static NetworkComponent getNet() {
        return netComponent;
    }
}
