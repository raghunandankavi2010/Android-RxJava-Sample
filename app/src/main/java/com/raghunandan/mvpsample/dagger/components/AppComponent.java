package com.raghunandan.mvpsample.dagger.components;

import com.raghunandan.mvpsample.dagger.modules.AppModule;
import com.raghunandan.mvpsample.model.PostsAPI;
import com.raghunandan.mvpsample.ui.activities.MainActivity;
import com.raghunandan.mvpsample.ui.activities.PostsActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    void inject(MainActivity activity);
    void inject(PostsActivity activity);

    PostsAPI providePostsAPI();
}
