package com.anupcowkur.mvpsample.model;

import com.anupcowkur.mvpsample.model.data.Post;

import java.util.List;

import retrofit.http.GET;
import rx.Observable;


public interface PostsAPI {
    @GET("/posts")
    Observable<List<Post>> getPostsList();
}

