package com.anupcowkur.mvpsample.dagger.modules;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.anupcowkur.mvpsample.dagger.UserScope;
import com.anupcowkur.mvpsample.model.PostsAPI;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okio.Buffer;

/**
 * Created by Raghunandan on 15-12-2015.
 */
@Module
public class NetworkModule {

    @Provides
    @Named("cached")
    @UserScope
    Cache provideOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache =null;
        try {
             cache = new Cache(application.getCacheDir(), cacheSize);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return cache;
    }

    @Provides
    @UserScope
    OkHttpClient provideOkHttpClient() {
        final OkHttpClient client = new OkHttpClient();
         client.setConnectTimeout(5, TimeUnit.SECONDS); // connect timeout
        client.setReadTimeout(5, TimeUnit.SECONDS);

        client.setConnectTimeout(5, TimeUnit.SECONDS); // connect timeout
        client.setReadTimeout(5, TimeUnit.SECONDS);

        client.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request originalRequest = chain.request(); //Current Request

               // Log.i("Body",bodyToString(originalRequest));
                Log.i("SSL Factory",""+client.getSslSocketFactory());
                Log.i("Host Name",""+client.getHostnameVerifier());
                Response response = chain.proceed(originalRequest); //Get response of the request


                //I am logging the response body in debug mode. When I do this I consume the response (OKHttp only lets you do this once) so i have re-build a new one using the cached body
                String bodyString = response.body().string();
                //Log.i("Response Body", bodyString);

                //Log.i("NetworkModule", String.format("Sending request %s with headers %s ", originalRequest.url(), originalRequest.headers()));
                Log.i("Response", (String.format("Got response HTTP %s %s \n\n with body %s \n\n with headers %s ", response.code(), response.message(), bodyString, response.headers())));
                response = response.newBuilder().body(ResponseBody.create(response.body().contentType(), bodyString)).build();


                return response;
            }
        });
        return client;
    }

    private static String bodyToString(final Request request){

        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    @Provides
    @UserScope
    PostsAPIGet providePostsApi(PostsAPI postsAPI)

    {
        return new PostsAPIGet(postsAPI);
    }
}


