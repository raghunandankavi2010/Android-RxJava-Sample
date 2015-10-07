package com.anupcowkur.mvpsample.ui.activities;

import com.anupcowkur.mvpsample.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends Activity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.show_posts_button)
    public void OnListSampleButtonClick() {
        Intent intent = new Intent(this, PostsActivity.class);
        startActivity(intent);
    }
}
