package com.anupcowkur.mvpsample.ui.presenters;

import com.anupcowkur.mvpsample.RxBus;
import com.anupcowkur.mvpsample.events.ErrorEvent;
import com.anupcowkur.mvpsample.events.NewPostsEvent;
import com.anupcowkur.mvpsample.model.PostsAPI;
import com.anupcowkur.mvpsample.model.pojo.Post;
import com.anupcowkur.mvpsample.ui.activities.PostsActivity;
import com.anupcowkur.mvpsample.ui.screen_contracts.PostsScreen;

import javax.inject.Inject;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PostsPresenter {

    PostsAPI postsAPI;
    Observable<List<Post>> m;
    RxBus bus;

    @Inject
    public PostsPresenter(PostsAPI postsAPI) {
        this.postsAPI = postsAPI;
        bus = RxBus.getInstance();


    }

    public void loadPostsFromAPI() {
        m = postsAPI.getPostsObservable();

        m.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                .mainThread())
                .subscribe(new Subscriber<List<Post>>() {
                    @Override
                    public void onNext(List<Post> newPosts) {

                        bus.send(new NewPostsEvent(newPosts));
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        bus.send(new ErrorEvent(e));

                    }

                });


    }


}
