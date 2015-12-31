package com.anupcowkur.mvpsample.ui.presenters;

import android.content.Context;
import android.util.Log;

//import com.anupcowkur.mvpsample.RxBus;
import com.anupcowkur.mvpsample.events.ErrorEvent;
import com.anupcowkur.mvpsample.events.NewPostsEvent;
import com.anupcowkur.mvpsample.model.PostsAPI;
import com.anupcowkur.mvpsample.model.pojo.Post;
import com.anupcowkur.mvpsample.ui.activities.PostsActivity;
import com.anupcowkur.mvpsample.ui.screen_contracts.PostsScreen;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class PostsPresenter {

    private PostsAPI postsAPI;
    private Observable<List<Post>> m;
    //RxBus bus;
    private CompositeSubscription compositeSubscription = new CompositeSubscription();
    private List<Post> postList = new ArrayList<>();

    public void setPostList(List<Post> mpostList) {
        this.postList.addAll(mpostList);
    }

    public List<Post> getPostList() {
        return postList;
    }

    PostsScreen getdetails;

    @Inject
    public PostsPresenter(PostsAPI postsAPI) {
        this.postsAPI = postsAPI;
       // bus = RxBus.getInstance();


    }

    public void loadPostsFromAPI() {
        m = postsAPI.getPostsObservable();

        //compositeSubscription.add(m);

        compositeSubscription.add(m.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                .mainThread())
                .subscribe(new Subscriber<List<Post>>() {
                    @Override
                    public void onNext(List<Post> newPosts) {
                        Log.i("List fetched","Yeah");
                        setPostList(newPosts);
                        getdetails.onNext(newPosts);
                        postsAPI.resetCache();
                       // bus.send(new NewPostsEvent(newPosts));
                    }

                    @Override
                    public void onCompleted() {
                        Log.i("Completed","Completed");
                        getdetails.onCompleted();
                        //bus.send("Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("ERROR","Something Wrong");
                        postsAPI.resetCache();
                        getdetails.onError(e);
                       // bus.send(new ErrorEvent(e));

                    }


                }));


    }

    public void unSubScribe()
    {
        compositeSubscription.unsubscribe();
        getdetails = null;
    }



    public void setContext(PostsScreen postsActivity)
    {
        getdetails =  postsActivity;

    }

}
