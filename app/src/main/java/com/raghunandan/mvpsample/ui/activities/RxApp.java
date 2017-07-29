package com.raghunandan.mvpsample.ui.activities;

/**
 * Created by Raghunandan on 07-10-2015.
 */
import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;


public class RxApp extends Application {

    /*@Singleton
    @Component(modules = NetworkModule.class)
    public interface NetworkComponent {
        PostsPresenter getAllPosts();
    }

    private NetworkComponent mComponent = null;*/

    private static RxApp sInstance;
    public static RxApp get() {
        return sInstance;
    }

    private RefWatcher refWatcher;
    @Override
    public void onCreate() {
        super.onCreate();
        refWatcher = LeakCanary.install(this);
       /* if (mComponent == null) {

            mComponent = DaggerRxApp_NetworkComponent.create();
        }
*/
        sInstance = (RxApp) getApplicationContext();
    }

/*    public NetworkComponent component() {
        return mComponent;
    }

    // This allows providing mock NetworkComponent from test
    public void setComponent(NetworkComponent component) {
        mComponent = component;
    }*/


}
