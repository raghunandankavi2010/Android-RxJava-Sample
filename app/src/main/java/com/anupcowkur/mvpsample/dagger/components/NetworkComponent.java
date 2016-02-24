package com.anupcowkur.mvpsample.dagger.components;

import android.support.v7.app.AppCompatActivity;

import com.anupcowkur.mvpsample.dagger.UserScope;
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
@UserScope
@Component(dependencies = AppCompatActivity.class,modules =NetworkModule.class)
public interface NetworkComponent {
    void inject(PostsAPI postsAPI);
    void inject(PostsPresenter postsPresenter);





}