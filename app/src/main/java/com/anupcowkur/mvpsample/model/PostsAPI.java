package com.anupcowkur.mvpsample.model;

import android.util.Log;

import com.anupcowkur.mvpsample.dagger.DaggerInjector;
import com.anupcowkur.mvpsample.model.pojo.Post;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import rx.Observable;
import rx.Subscriber;
import retrofit.RxJavaCallAdapterFactory;

public class PostsAPI {


    @Inject
    OkHttpClient okHttpClient;


    @Inject
    public PostsAPI()
    {
          DaggerInjector.getNet().inject(this);
    }

    public interface PostService {

        @GET("/posts")
        Observable<List<Post>> getPosts();

    }

    private OnSubscribeRefreshingCache<List<Post>> cacher;


    private Observable<List<Post>> postsObservable ;

    public Observable<List<Post>> getPost()
    {
        postsObservable = getApi().getPosts();
       /* postsObservable = Observable.create(new Observable.OnSubscribe<List<Post>>() {
        @Override
        public void call(final Subscriber<? super List<Post>> subscriber) {

            Log.i("Size",""+"hello");
            Call<List<Post>> response = getApi().getPosts();
            response.enqueue(new Callback<List<Post>>() {
                @Override
                public void onResponse(Response<List<Post>> resp) {
                    // Get result Repo from response.body()
                    //Log.i("Response ",""+ resp.body());

                    try {
                        //total_pages = resp.body().getTotal_pages();
                        List<Post> reviewslist = (List<Post>) resp.body();
                        if(reviewslist.size()>0) {
                            subscriber.onNext(reviewslist);

                        }else {
                            subscriber.onError(new Throwable("Empty List"));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Throwable t) {
                    subscriber.onError(t);
                }
            });
        }
    });*/
        postsObservable.cache();
        Log.i("get api",""+"called");
        return postsObservable;
    }


 /*   public Observable<List<Post>> getPostsObservable() {

        postsObservable = getApi().getPosts();

        postsObservable.cache();
        cacher =
                new OnSubscribeRefreshingCache<List<Post>>(postsObservable);
        postsObservable = Observable.create(cacher);

        return postsObservable;
    }*/

    public void resetCache() {

        Log.i("Resetting Cache","Yes");
        cacher.reset();
    }

    PostService getApi() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://jsonplaceholder.typicode.com")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();


        return retrofit.create(PostService.class);

    }

    public static class OnSubscribeRefreshingCache<T> implements Observable.OnSubscribe<T> {

        private final AtomicBoolean refresh = new AtomicBoolean(true);
        private final Observable<T> source;
        private volatile Observable<T> current;

        public OnSubscribeRefreshingCache(Observable<T> source) {
            this.source = source;
            this.current = source;
        }

        public void reset() {
            refresh.set(true);
        }

        @Override
        public void call(Subscriber<? super T> subscriber) {
            if (refresh.compareAndSet(true, false)) {
                current = source.cache();
                Log.i("Using Cache","Current from cache");
            }
            current.unsafeSubscribe(subscriber);
        }

    }



}
