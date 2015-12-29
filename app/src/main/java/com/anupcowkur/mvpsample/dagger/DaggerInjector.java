package com.anupcowkur.mvpsample.dagger;

import com.anupcowkur.mvpsample.dagger.components.AppComponent;

import com.anupcowkur.mvpsample.dagger.components.DaggerAppComponent;
import com.anupcowkur.mvpsample.dagger.components.DaggerNetworkComponent;
import com.anupcowkur.mvpsample.dagger.components.NetworkComponent;
import com.anupcowkur.mvpsample.dagger.modules.AppModule;
import com.anupcowkur.mvpsample.dagger.modules.NetworkModule;

public class DaggerInjector {
    private static AppComponent appComponent = DaggerAppComponent.builder()
             .appModule(new AppModule())
             .build();

    public static AppComponent get() {
        return appComponent;
    }

    private static NetworkComponent netComponent = DaggerNetworkComponent.builder()

            .appModule(new AppModule())
            .build();

    public static NetworkComponent getNet() {
        return netComponent;
    }
}
