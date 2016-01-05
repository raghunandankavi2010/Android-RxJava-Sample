package com.anupcowkur.mvpsample.ui.presenters;

import android.content.Context;
import android.util.Log;

//import com.anupcowkur.mvpsample.RxBus;
import com.anupcowkur.mvpsample.RxBus;
import com.anupcowkur.mvpsample.dagger.DaggerInjector;
import com.anupcowkur.mvpsample.events.ErrorEvent;
import com.anupcowkur.mvpsample.events.NewPostsEvent;
import com.anupcowkur.mvpsample.model.PostsAPI;
import com.anupcowkur.mvpsample.model.pojo.Post;
import com.anupcowkur.mvpsample.ui.activities.PostsActivity;
import com.anupcowkur.mvpsample.ui.screen_contracts.PostsScreen;

import javax.inject.Inject;
import javax.inject.Singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

@Singleton
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
    RxBus rxBus;

    @Inject
    public PostsPresenter(PostsAPI postsAPI) {
        this.postsAPI = postsAPI;
        DaggerInjector.get().inject(this);
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
                       /* setPostList(newPosts);*/
                        getdetails.onNext(newPosts);
                        //rxBus.send(new NewPostsEvent(newPosts));
                        postsAPI.resetCache();

                    }

                    @Override
                    public void onCompleted() {
                        Log.i("Completed","Completed");
                        getdetails.onCompleted();
                        rxBus.send("Completed");
                        //bus.send("Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("ERROR","Something Wrong");
                        postsAPI.resetCache();
                        //rxBus.send(new ErrorEvent(e));
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

    private List<Post> listData;
    public void setListData(List<Post> listData) {
        this.listData = listData;
    }

    public List<Post> getListData() {
        return this.listData;
    }
}
