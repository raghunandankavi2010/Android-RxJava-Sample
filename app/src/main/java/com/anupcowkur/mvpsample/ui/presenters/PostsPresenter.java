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

import de.greenrobot.event.EventBus;
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

    PostsScreen getdetails;

    @Inject
    public PostsPresenter(PostsAPI postsAPI) {
        this.postsAPI = postsAPI;
        Log.i("Presenter","called");
        DaggerInjector.get().inject(this);
       // bus = RxBus.getInstance();


    }

/*    public void getRequest()
    {
        m= postsAPI.getPostsObservable();
        compositeSubscription.add(m.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                .mainThread())
                .subscribe(new MySubscriber()));
    }*/

    public void loadPostsFromAPI() {
        m = postsAPI.getPost();

        //compositeSubscription.add(m);

        compositeSubscription.add(m.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                .mainThread())
                .subscribe(new MySubscriber()));

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


    public class  MySubscriber extends Subscriber<List<Post>>
    {

        @Override
        public void onNext(List<Post> newPosts) {
            Log.i("List fetched","Yeah");
            //postsAPI.resetCache();
            EventBus.getDefault().post(newPosts);



        }

        @Override
        public void onCompleted() {
            Log.i("Completed","Completed");

        }

        @Override
        public void onError(Throwable e) {
            Log.i("ERROR","Something Wrong");
            //postsAPI.resetCache();
            EventBus.getDefault().post(e);


        }



    }
}
