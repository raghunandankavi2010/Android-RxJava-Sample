package com.anupcowkur.mvpsample.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.anupcowkur.mvpsample.R;
import com.anupcowkur.mvpsample.dagger.DaggerInjector;
import com.anupcowkur.mvpsample.ui.presenters.MainPresenter;
import com.anupcowkur.mvpsample.ui.screen_contracts.MainScreen;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainScreen {

    @Inject
    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
       /* Button showPosts = (Button)this.findViewById(R.id.show_posts_button);
        showPosts.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {

            }
        });*/

        DaggerInjector.get().inject(this);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.show_posts_button)
    public void OnListSampleButtonClick() {
        Toast.makeText(getApplicationContext(),"Posts Activity",Toast.LENGTH_SHORT).show();
        mainPresenter.OnShowPostsButtonClick(MainActivity.this);
    }

    @Override
    public void launchPostsActivity() {
        Intent intent = new Intent(this, PostsActivity.class);
        startActivity(intent);
    }

}
