package com.anupcowkur.mvpsample.ui.activities;

import com.anupcowkur.mvpsample.model.PostsAPI;
import com.squareup.okhttp.OkHttpClient;
import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.Retrofit;

/**
 * Created by Raghunandan on 07-10-2015.
 */
@Module
public class NetworkModule {

    @Provides
    PostsAPI providePostsApi() {
        OkHttpClient okHttpClient = new OkHttpClient();


        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl("http://jsonplaceholder.typicode.com")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(PostsAPI.class);
    }
}