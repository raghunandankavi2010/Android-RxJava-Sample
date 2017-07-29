package com.raghunandan.mvpsample.dagger.modules;

import com.raghunandan.mvpsample.RxBus;
import com.raghunandan.mvpsample.model.PostsAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    PostsAPI providePostsApi() {
        return new PostsAPI();
    }


    @Provides
    @Singleton
    RxBus provideRxBus() {
        return new RxBus();
    }

}
