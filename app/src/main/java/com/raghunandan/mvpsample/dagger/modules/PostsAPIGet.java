package com.raghunandan.mvpsample.dagger.modules;

import com.raghunandan.mvpsample.model.PostsAPI;

/**
 * Created by Raghunandan on 10-02-2016.
 */
public class PostsAPIGet {

    private PostsAPI postsAPI;

    public PostsAPIGet( PostsAPI postsAPI)
    {
        this.postsAPI = postsAPI;
    }

    public PostsAPI getSharedPreferences() {
        return this.postsAPI;
    }
}
