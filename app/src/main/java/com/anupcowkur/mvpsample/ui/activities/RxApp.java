package com.anupcowkur.mvpsample.ui.activities;

/**
 * Created by Raghunandan on 07-10-2015.
 */
import android.app.Application;

import com.anupcowkur.mvpsample.model.PostsAPI;
import com.anupcowkur.mvpsample.ui.presenters.PostsPresenter;


import javax.inject.Singleton;

import dagger.Component;


public class RxApp extends Application {

    @Singleton
    @Component(modules = NetworkModule.class)
    public interface NetworkComponent {
        PostsPresenter getAllPosts();
    }

    private NetworkComponent mComponent = null;

    private static RxApp sInstance;
    public static RxApp get() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (mComponent == null) {

            mComponent = DaggerRxApp_NetworkComponent.create();
        }

        sInstance = (RxApp) getApplicationContext();
    }

    public NetworkComponent component() {
        return mComponent;
    }

    // This allows providing mock NetworkComponent from test
    public void setComponent(NetworkComponent component) {
        mComponent = component;
    }
}
