package com.anupcowkur.mvpsample.dagger;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by Raghunandan on 08-01-2016.
 */
public class MVPApplication extends Application {

    private RefWatcher refWatcher;
    @Override public void onCreate() {
        super.onCreate();
        refWatcher = LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        MVPApplication application = (MVPApplication) context.getApplicationContext();
        return application.refWatcher;
    }


}
