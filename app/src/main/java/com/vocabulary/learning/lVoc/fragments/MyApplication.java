package com.vocabulary.learning.lVoc.fragments;

import com.activeandroid.ActiveAndroid;

/**
 * Created by mohamed on 26/03/16.
 */
public class MyApplication extends com.activeandroid.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }

}
