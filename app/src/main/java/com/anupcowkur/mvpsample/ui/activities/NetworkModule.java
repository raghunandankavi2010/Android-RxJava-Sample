package com.anupcowkur.mvpsample.ui.activities;

import com.anupcowkur.mvpsample.model.PostsAPI;
import com.anupcowkur.mvpsample.model.data.Post;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.GET;
import rx.Observable;
import retrofit.Retrofit;

import static java.lang.String.format;

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