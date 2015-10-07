package com.anupcowkur.mvpsample.model;

import android.util.Log;

import com.anupcowkur.mvpsample.model.data.Post;

import java.util.List;

import retrofit.http.GET;
import rx.Observable;

//public class PostsAPI {


     public interface PostsAPI {
        @GET("/posts")
        Observable<List<Post>> getPostsList();
    }

/*    private Observable<List<Post>> postsObservable = new RestAdapter.Builder()
            .setEndpoint("http://jsonplaceholder.typicode.com")
            .build().create(PostService.class).getPostsList();*/


  /*  public Observable<List<Post>> getPostsObservable() {
            return postsObservable;

    }*/
//}
