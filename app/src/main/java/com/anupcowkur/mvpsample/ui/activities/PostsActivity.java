package com.anupcowkur.mvpsample.ui.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.anupcowkur.mvpsample.R;
//import com.anupcowkur.mvpsample.RxBus;
import com.anupcowkur.mvpsample.dagger.DaggerInjector;
import com.anupcowkur.mvpsample.model.pojo.Post;
import com.anupcowkur.mvpsample.ui.adapters.PostsListAdapter;
import com.anupcowkur.mvpsample.ui.decorators.DividerItemDecoration;
import com.anupcowkur.mvpsample.ui.presenters.PostsPresenter;
import com.anupcowkur.mvpsample.ui.screen_contracts.PostsScreen;

import java.util.List;


import butterknife.Bind;
import butterknife.ButterKnife;


import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class PostsActivity extends AppCompatActivity implements PostsScreen {

    private PostsListAdapter postsListAdapter;
    private static final String REQUEST_PEDNING = "request_pending";

    private LoaderManager.LoaderCallbacks<List<Post>>
            mLoaderCallbacks =
            new LoaderManager.LoaderCallbacks<List<Post>>() {
                @Override
                public Loader<List<Post>> onCreateLoader(
                        int id, Bundle args) {


                    return postsPresenter;
                }
                @Override
                public void onLoadFinished(
                        Loader<List<Post>> loader, List<Post> data) {
                    // Display our data, for instance updating our adapter

                    errorView.setVisibility(View.GONE);
                    postsRecyclerView.setVisibility(View.VISIBLE);
                    Log.i("Size in on Next",""+data.size());
                    if (loadMore) {
                        loadMore = false;
                        Log.i("Load More",""+loadMore);
                        postsListAdapter.remove();
                    }
                    postsListAdapter.addPosts(data);

                    mRequestPending = false;
                }
                @Override
                public void onLoaderReset(Loader<List<Post>> loader) {
                    // Loader reset, throw away our data,
                    // unregister any listeners, etc.
                    postsListAdapter.addPosts(null);
                    //loader.reset();
                    // Of course, unless you use destroyLoader(),
                    // this is called when everything is already dying
                    // so a completely empty onLoaderReset() is
                    // totally acceptable
                }
            };



    PostsPresenter postsPresenter;

    @Bind(R.id.posts_recycler_view)
    RecyclerView postsRecyclerView;

    @Bind(R.id.error_view)
    TextView errorView;


    private boolean loadMore, mRequestPending;


    @OnClick(R.id.button)
    public void OnListSampleButtonClick() {

        loadMore = true;
        mRequestPending = true;
        postsListAdapter.add(null);
        Loader loader =getSupportLoaderManager().getLoader(0);
        loader.reset();
        loader.forceLoad();
        /*postsPresenter = new PostsPresenter(this);
        postsPresenter.setContext(this);
        postsPresenter.refresh(true);*/


    }

    private Handler handler;

    //RxBus rxBus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_posts);

        postsPresenter = new PostsPresenter(PostsActivity.this);
        postsPresenter.setContext(PostsActivity.this);

        //rxBus = RxBus.getInstance();
        handler = new Handler();
        DaggerInjector.get().inject(this);
        //
        ButterKnife.bind(this);



        initRecyclerView();
       /* if (savedInstanceState != null) {
            boolean bool = savedInstanceState.getBoolean(REQUEST_PEDNING, false);
            *//* Continuing to load data from server. Request was already made *//*
            if (bool) {
                loadMore = savedInstanceState.getBoolean("loadMore", loadMore);
                postsPresenter.loadPostsFromAPI();
                Toast.makeText(getApplicationContext(), "Continuing Subscription", Toast.LENGTH_SHORT).show();
            }
            *//* list after rotation. Handling configuration change *//*
            if (savedInstanceState.containsKey("saving list")) {

                Toast.makeText(getApplicationContext(), "List display", Toast.LENGTH_SHORT).show();
                List<Post> post = savedInstanceState.getParcelableArrayList("list");
                if (post != null && post.size() > 0)
                    postsListAdapter.addPosts(post);
            }
            *//* cache is reset no data. so try getting data from server again *//*
            else {
                postsPresenter.loadPostsFromAPI();
                mRequestPending = true;

            }
        } else {
            postsPresenter.loadPostsFromAPI();
            mRequestPending = true;

        }*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //postsPresenter.unSubScribe();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void initRecyclerView() {
        postsRecyclerView.setHasFixedSize(true);
        postsRecyclerView.setLayoutManager(new LinearLayoutManager(postsRecyclerView.getContext()));
        postsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        postsRecyclerView.addItemDecoration(new DividerItemDecoration(postsRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL_LIST));
        postsListAdapter = new PostsListAdapter();
        postsRecyclerView.setAdapter(postsListAdapter);
        getSupportLoaderManager().initLoader(0, null, mLoaderCallbacks);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
       /* if (postsListAdapter.getPosts().size() > 0) {
            outState.putString("saving list", "yes");
            //postsPresenter.setListData(postsListAdapter.getPosts());
            outState.putParcelableArrayList("list", (ArrayList<Post>) postsListAdapter.getPosts());
        }
        outState.putBoolean(REQUEST_PEDNING, mRequestPending);
        outState.putBoolean("loadMore", loadMore);*/


    }

    /* public void onEventMainThread(List<Post> newPosts) {
         errorView.setVisibility(View.GONE);
         postsRecyclerView.setVisibility(View.VISIBLE);
         Log.i("Size in on Next",""+newPosts.size());
         if (loadMore) {
             loadMore = false;
             Log.i("Load More",""+loadMore);
             postsListAdapter.remove();
         }
         postsListAdapter.addPosts(newPosts);

         mRequestPending = false;
    }*/

    public void onEventMainThread(Throwable errorEvent) {
        showError(errorEvent);
    }

    private void hideError() {
        postsRecyclerView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

    private void showError(Throwable errorEvent) {
        errorEvent.printStackTrace();
        mRequestPending = false;
        postsRecyclerView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        if (loadMore) {
            loadMore = false;
            Log.i("Load More",""+loadMore);
            postsListAdapter.remove();
        }
    }




    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(List<Post> posts) {

    }

    @Override
    public void onCompleted() {

    }
}
