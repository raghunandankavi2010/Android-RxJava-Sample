package com.raghunandan.mvpsample.ui.activities;

import android.util.Log;

import com.raghunandan.mvpsample.model.PostsAPI;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okio.Buffer;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Raghunandan on 07-10-2015.
 */
@Module
public class NetworkModule {

    @Provides
    PostsAPI providePostsApi() {
        OkHttpClient okHttpClient = new OkHttpClient();

        okHttpClient.setConnectTimeout(5, TimeUnit.SECONDS); // connect timeout
        okHttpClient.setReadTimeout(5, TimeUnit.SECONDS);

        okHttpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request originalRequest = chain.request(); //Current Request
                Log.i("Body",bodyToString(originalRequest));
                Response response = chain.proceed(originalRequest); //Get response of the request


                //I am logging the response body in debug mode. When I do this I consume the response (OKHttp only lets you do this once) so i have re-build a new one using the cached body
                String bodyString = response.body().string();
                Log.i("Response Body", bodyString);

                Log.i("NetworkModule", String.format("Sending request %s with headers %s ", originalRequest.url(), originalRequest.headers()));
                Log.i("", (String.format("Got response HTTP %s %s \n\n with body %s \n\n with headers %s ", response.code(), response.message(), bodyString, response.headers())));
                response = response.newBuilder().body(ResponseBody.create(response.body().contentType(), bodyString)).build();


                return response;
            }
        });

        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl("http://jsonplaceholder.typicode.com")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())

                .build();

        return retrofit.create(PostsAPI.class);
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
}