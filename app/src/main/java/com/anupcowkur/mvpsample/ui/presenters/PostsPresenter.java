package com.anupcowkur.mvpsample.ui.presenters;

import com.anupcowkur.mvpsample.model.PostsAPI;
import com.anupcowkur.mvpsample.model.data.Post;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

import rx.subjects.AsyncSubject;

@Singleton
public class PostsPresenter {


    PostsAPI postsAPI;

    private AsyncSubject<List<Post>> mAsyncSubject;

    @Inject
    public PostsPresenter(PostsAPI api) {
        postsAPI = api;
    }

    public void reset() {
        mAsyncSubject = null;

    }

    public Observable<List<Post>> getRequest() {
        return mAsyncSubject;
    }


    public Observable<List<Post>> getAllPosts() {

        if (mAsyncSubject == null) {
            mAsyncSubject = AsyncSubject.create();

            postsAPI.getPostsList()
                    .subscribe(mAsyncSubject);
        }
        return mAsyncSubject;
    }
}
