package com.vocabulary.learning.lVoc.views.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.vocabulary.learning.lVoc.R;
import com.vocabulary.learning.lVoc.fragments.MainActivityFragment;
import com.vocabulary.learning.lVoc.fragments.ScoreFragment;

/**
 * Created by mohamed on 29/03/16.
 */
public class MainActivityPagerAdapter extends FragmentStatePagerAdapter {
    Context main_context;

    public MainActivityPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        main_context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new MainActivityFragment();
        else
            return new ScoreFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String[] tabs = main_context.getResources().getStringArray(R.array.tabs);
        return tabs[position];
    }
}