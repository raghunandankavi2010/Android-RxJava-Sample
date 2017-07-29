package com.raghunandan.mvpsample.ui.presenters;

import com.raghunandan.mvpsample.ui.screen_contracts.MainScreen;

import javax.inject.Inject;

public class MainPresenter {

    @Inject
    public MainPresenter() {
    }

    public void OnShowPostsButtonClick(MainScreen mainScreen) {
        mainScreen.launchPostsActivity();
    }

}
