package com.raghunandan.mvpsample.ui.screen_contracts;

import com.raghunandan.mvpsample.model.pojo.Post;

import java.util.List;

public interface PostsScreen {

    void onError(Throwable e);

    void onNext(List<Post> posts);

    void onCompleted();

}
