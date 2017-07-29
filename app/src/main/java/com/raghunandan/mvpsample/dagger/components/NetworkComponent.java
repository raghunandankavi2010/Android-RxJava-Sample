package com.raghunandan.mvpsample.dagger.components;

import android.support.v7.app.AppCompatActivity;

import com.raghunandan.mvpsample.dagger.UserScope;
import com.raghunandan.mvpsample.dagger.modules.NetworkModule;
import com.raghunandan.mvpsample.model.PostsAPI;
import com.raghunandan.mvpsample.ui.presenters.PostsPresenter;

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