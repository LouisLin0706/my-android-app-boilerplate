package com.smple.app_litho_sample;

import android.app.Application;

import com.facebook.soloader.SoLoader;

/**
 * Created by Louis on 2018/4/13.
 */

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SoLoader.init(this, false);
    }
}
