package com.anupcowkur.mvpsample.ui.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.anupcowkur.mvpsample.R;
//import com.anupcowkur.mvpsample.RxBus;
import com.anupcowkur.mvpsample.dagger.DaggerInjector;
import com.anupcowkur.mvpsample.events.ErrorEvent;
import com.anupcowkur.mvpsample.events.NewPostsEvent;
import com.anupcowkur.mvpsample.model.pojo.Post;
import com.anupcowkur.mvpsample.ui.adapters.PostsListAdapter;
import com.anupcowkur.mvpsample.ui.decorators.DividerItemDecoration;
import com.anupcowkur.mvpsample.ui.presenters.PostsPresenter;
import com.anupcowkur.mvpsample.ui.screen_contracts.PostsScreen;

import java.util.ArrayList;
import java.util.List;


import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

import butterknife.OnClick;
import retrofit.http.POST;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

public class PostsActivity extends AppCompatActivity implements PostsScreen {

    private PostsListAdapter postsListAdapter;
    private static final String REQUEST_PEDNING = "request_pending";

    @Override
    public void onError(Throwable e) {
        mRequestPending=false;
        postsRecyclerView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void onNext(final List<Post> newPosts) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (loadMore) {
                    loadMore = false;
                    postsListAdapter.remove();
                }
                mRequestPending =false;
                postsListAdapter.addPosts(newPosts);
            }
        },5000);

    }

    @Override
    public void onCompleted() {
       mRequestPending =false;
        Toast.makeText(PostsActivity.this.getApplicationContext(), "Completed", Toast.LENGTH_SHORT)
                .show();
    }

    @Inject
    PostsPresenter postsPresenter;

    @InjectView(R.id.posts_recycler_view)
    RecyclerView postsRecyclerView;

    @InjectView(R.id.error_view)
    TextView errorView;


    private boolean loadMore,mRequestPending;


    @OnClick(R.id.button)
    public void OnListSampleButtonClick() {
        loadMore = true;
        mRequestPending =true;
        postsListAdapter.add(null);
        postsPresenter.loadPostsFromAPI();
    }
    private Handler handler;

    //RxBus rxBus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_posts);
        //rxBus = RxBus.getInstance();
         handler = new Handler();
        DaggerInjector.get().inject(this);
        postsPresenter.setContext(this);
        ButterKnife.inject(this);

        initRecyclerView();
        if (savedInstanceState != null) {
            boolean bool = savedInstanceState.getBoolean(REQUEST_PEDNING, false);

            if(bool)
            {
                loadMore = savedInstanceState.getBoolean("loadMore", loadMore);
                postsPresenter.loadPostsFromAPI();
                Toast.makeText(getApplicationContext(),"Continuing Subscription",Toast.LENGTH_SHORT).show();
            }
            if (savedInstanceState.containsKey("list")) {

                Toast.makeText(getApplicationContext(),"List display",Toast.LENGTH_SHORT).show();
                List<Post> post = savedInstanceState.getParcelableArrayList("list");
                if (post != null && post.size() > 0)
                    postsListAdapter.addPosts(post);
            }
        } else {
            postsPresenter.loadPostsFromAPI();
            mRequestPending =true;

        }
       /* rxBus.toObserverable().subscribe(new Action1<Object>() {
            @Override
            public void call(Object event) {

                if(event instanceof String)
                {
                    Toast.makeText(PostsActivity.this.getApplicationContext(),"Completed",Toast.LENGTH_SHORT)
                            .show();
                }
                if(event instanceof NewPostsEvent) {
                    NewPostsEvent newPostsEvent = (NewPostsEvent)event;
                    postsListAdapter.addPosts(newPostsEvent.getPosts());

                }else if(event instanceof ErrorEvent) {
                    postsRecyclerView.setVisibility(View.VISIBLE);
                    errorView.setVisibility(View.GONE);
                }
            }

            });*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        //EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        postsPresenter.unSubScribe();
        //EventBus.getDefault().unregister(this);
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("list", (ArrayList<Post>) postsListAdapter.getPosts());
        outState.putBoolean(REQUEST_PEDNING, mRequestPending);
        outState.putBoolean("loadMore", loadMore);


    }

     /*public void onEventMainThread(NewPostsEvent newPostsEvent) {
        hideError();
        postsListAdapter.addPosts(newPostsEvent.getPosts());
    }

    public void onEventMainThread(ErrorEvent errorEvent) {
        showError();
    }

    private void hideError() {
        postsRecyclerView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

    private void showError() {
        postsRecyclerView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        postsPresenter.reset();
    }
*/
  /*  @Override
    public void getData(List<Post> mPost) {
        postsListAdapter.addPosts(mPost);
    }

    @Override
    public void getError(Throwable mError) {

        postsRecyclerView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void getCompleted() {

    }*/


}
