package com.boilerplate.mvvm_sample.ui.di.builder;

import com.boilerplate.mvvm_sample.ui.about.AboutFragmentProvider;
import com.boilerplate.mvvm_sample.ui.feed.FeedFragmentProvider;
import com.boilerplate.mvvm_sample.ui.main.MainActivity;
import com.boilerplate.mvvm_sample.ui.main.MainActivityModel;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Louis on 2018/4/10.
 */
@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {MainActivityModel.class, FeedFragmentProvider.class, AboutFragmentProvider.class})
    abstract MainActivity bindMainActivity();

}
