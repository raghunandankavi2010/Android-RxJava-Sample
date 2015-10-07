package com.anupcowkur.mvpsample.ui.activities;

import com.anupcowkur.mvpsample.R;

import com.anupcowkur.mvpsample.model.PostsAPI;

import com.anupcowkur.mvpsample.model.data.Post;
import com.anupcowkur.mvpsample.ui.adapters.PostsListAdapter;
import com.anupcowkur.mvpsample.ui.decorators.DividerItemDecoration;
import com.anupcowkur.mvpsample.ui.presenters.PostsPresenter;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;
import retrofit.http.GET;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class PostsActivity extends Activity {

    @Inject
    PostsPresenter postsPresenter;

    PostsAPI api;

    @InjectView(R.id.posts_recycler_view)
    RecyclerView postsRecyclerView;

    @InjectView(R.id.error_view)
    TextView errorView;

    PostsListAdapter postsListAdapter;
    private static final String REQUEST_PENDING = "requestPending";

    private CompositeSubscription mSubscriptions = new CompositeSubscription();
    private boolean mRequestPending;


    private interface PostService {
        @GET("/posts")
        Observable<List<Post>> getPostsList();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_posts);


        //DaggerInjector.get().inject(this);
        ButterKnife.inject(this);
        postsPresenter = RxApp.get().component().getAllPosts();
        initRecyclerView();

        if (savedInstanceState != null && savedInstanceState.getBoolean(REQUEST_PENDING, false)) {

            if (postsPresenter.getRequest() != null) {

                mSubscriptions.add(
                        postsPresenter.getRequest()
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new PostsSubscriber()));

                Log.i("PostsActivity", "Not null");
                Toast.makeText(getApplicationContext(), "not null", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), " null", Toast.LENGTH_SHORT).show();
                Log.i("PostsActivity", "null");
            }
        } else {


            fetchPosts();
        }

    }

    private void fetchPosts() {
        Log.i("PostsActivity", "Fetch data from RepoInputFragment");
        mRequestPending = true;

        mSubscriptions.add(
                postsPresenter.getAllPosts()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new PostsSubscriber()));


    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void initRecyclerView() {
        postsRecyclerView.setHasFixedSize(true);
        postsRecyclerView.setLayoutManager(new LinearLayoutManager(postsRecyclerView.getContext()));
        postsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        postsRecyclerView.addItemDecoration(new DividerItemDecoration(postsRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL_LIST));
        postsListAdapter = new PostsListAdapter();
        postsRecyclerView.setAdapter(postsListAdapter);
    }


    private class PostsSubscriber extends Subscriber<List<Post>> {

        @Override
        public void onNext(List<Post> posts) {
            Log.i("PostActivity", "received data from model");
            postsListAdapter.addPosts(posts);
            postsListAdapter.notifyDataSetChanged();
        }

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Log.i("PostActivity", "Data Not Received");
            errorView.setVisibility(View.VISIBLE);
            postsPresenter.reset();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(REQUEST_PENDING, mRequestPending);
    }


}
