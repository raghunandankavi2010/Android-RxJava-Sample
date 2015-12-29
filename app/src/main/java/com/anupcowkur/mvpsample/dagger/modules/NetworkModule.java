package com.anupcowkur.mvpsample.dagger.modules;

import android.app.Application;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Raghunandan on 15-12-2015.
 */
@Module
public class NetworkModule {

    @Provides
    @Named("cached")
    @Singleton
    Cache provideOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache =null;
        try {
             cache = new Cache(application.getCacheDir(), cacheSize);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return cache;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(5, TimeUnit.SECONDS); // connect timeout
        client.setReadTimeout(5, TimeUnit.SECONDS);
        return client;
    }
}


