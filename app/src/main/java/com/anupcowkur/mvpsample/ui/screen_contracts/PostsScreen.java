package com.anupcowkur.mvpsample.ui.screen_contracts;

import com.anupcowkur.mvpsample.model.pojo.Post;

import java.util.List;

import rx.Observable;

public interface PostsScreen {

    void onError(Throwable e);

    void onNext(List<Post> posts);

    void onCompleted();

}
