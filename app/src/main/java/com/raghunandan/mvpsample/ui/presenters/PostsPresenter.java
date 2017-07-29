package com.raghunandan.mvpsample.ui.presenters;

import android.content.Context;
import android.support.v4.content.Loader;
import android.util.Log;

import com.raghunandan.mvpsample.dagger.DaggerInjector;
import com.raghunandan.mvpsample.model.PostsAPI;
import com.raghunandan.mvpsample.model.pojo.Post;
import com.raghunandan.mvpsample.ui.screen_contracts.PostsScreen;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

//import com.raghunandan.mvpsample.RxBus;


public class PostsPresenter extends Loader<List<Post>> {

    @Inject
    PostsAPI postsAPI;
    private Observable<List<Post>> m;

    boolean mLoadMore;
    //RxBus bus;
    private CompositeSubscription compositeSubscription ;
    private List<Post> postList ;
    private List<Post> newData = new ArrayList<>();

    PostsScreen getdetails;

    public PostsPresenter(Context context) {
        super(context);
        DaggerInjector.getNet().inject(this);

    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
        compositeSubscription = new CompositeSubscription();
        m = postsAPI.getPost();
        compositeSubscription.add(m.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                .mainThread())
                .subscribe(new MySubscriber()));

    }



 /*   @Override
    public void deliverResult(List<Post> data) {
        // We’ll save the data for later retrieval
        postList = data;
        // We can do any pre-processing we want here
        // Just remember this is on the UI thread so nothing lengthy!
        super.deliverResult
    }*/
    @Override
    protected void onStartLoading() {;

        Log.i("New Data",""+newData.size());
        if(postList!=null )
        {
            Log.i("New Data",""+newData.size());
            deliverResult(newData);
        }

        if (takeContentChanged() || postList==null ) {
            // Something has changed or we have no data,
            // so kick off loading it
            forceLoad();


        }
    }

    @Override
    protected void onReset() {

        Log.i("Unsubscribed","Great");
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

            postList= newPosts;
            newData.addAll(postList);
            //forceLoad();
            Log.i("List fetched","Yeah"+newData.size());
            deliverResult(postList);
            //EventBus.getDefault().post(newPosts);

        }
        @Override
        public void onCompleted() {
            Log.i("Completed","Completed");
        }

        @Override
        public void onError(Throwable e) {
            Log.i("ERROR","Something Wrong");
            e.printStackTrace();
            //postsAPI.resetCache();
            //EventBus.getDefault().post(e);

        }

    }

/*    @Override
    public void deliverResult(List<Post> data)
    {
        *//*if(isReset())
        {

        }
        List<Post> oldData = postList;
        postList = data;*//*
        if(isStarted())
        {
            Log.i("...............","..........."+data.size());

            super.deliverResult(data);
        }
       *//* if(oldData!=null && oldData!=data)
        {

        }*//*
    }*/

}
