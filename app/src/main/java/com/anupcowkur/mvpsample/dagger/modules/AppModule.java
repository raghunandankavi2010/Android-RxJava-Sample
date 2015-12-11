package com.anupcowkur.mvpsample.dagger.modules;

import com.anupcowkur.mvpsample.model.PostsAPI;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;
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

}
