package com.boilerplate.mvvm_sample.ui.feed;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Louis on 2018/4/9.
 */
@Module
public abstract class FeedFragmentProvider {

    @ContributesAndroidInjector(modules = FeedFragmentModule.class)
    abstract FeedFragment provideFeedFragmentFactory();
}
