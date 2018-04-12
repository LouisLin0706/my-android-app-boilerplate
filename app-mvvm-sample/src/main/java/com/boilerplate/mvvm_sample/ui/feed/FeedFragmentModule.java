package com.boilerplate.mvvm_sample.ui.feed;

import android.arch.lifecycle.ViewModelProvider;

import com.boilerplate.mvvm_sample.ui.ViewModelProviderFactory;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Louis on 2018/4/9.
 */
@Module
public class FeedFragmentModule {

    @Provides
    FeedViewModel feedViewModel() {
        return new FeedViewModel();
    }


    @Provides
    ViewModelProvider.Factory provideFeedViewModel(FeedViewModel feedViewModel) {
        return new ViewModelProviderFactory<>(feedViewModel);
    }
}
