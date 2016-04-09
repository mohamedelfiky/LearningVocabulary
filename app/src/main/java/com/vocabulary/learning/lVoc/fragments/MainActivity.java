package com.vocabulary.learning.lVoc.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.vocabulary.learning.lVoc.R;
import com.vocabulary.learning.lVoc.utils.LVoc;
import com.vocabulary.learning.lVoc.views.adapters.MainActivityPagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.tablayout)
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (fab != null)
            fab.setOnClickListener(this);

        MainActivityPagerAdapter adapter = new MainActivityPagerAdapter(this, getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        SharedPreferences prefs = getSharedPreferences(LVoc.PACKAGE_NAME, Context.MODE_PRIVATE);

        if (prefs.getInt(LVoc.NOTIFICATION_COUNT, 7) == 1)
            fab.setVisibility(View.VISIBLE);
        else
            fab.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                startActivity(new Intent(this, AssignmentActivity.class));
                break;
        }
    }
}
