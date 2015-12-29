package com.anupcowkur.mvpsample.dagger.components;

import com.anupcowkur.mvpsample.dagger.modules.AppModule;
import com.anupcowkur.mvpsample.dagger.modules.NetworkModule;
import com.anupcowkur.mvpsample.model.PostsAPI;
import com.anupcowkur.mvpsample.ui.activities.MainActivity;
import com.anupcowkur.mvpsample.ui.activities.PostsActivity;
import com.anupcowkur.mvpsample.ui.presenters.PostsPresenter;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Raghunandan on 15-12-2015.
 */
@Component(modules = {NetworkModule.class,AppModule.class})
@Singleton
public interface NetworkComponent {
    void inject(PostsAPI postsAPI);

}