package com.boilerplate.mvvm_sample.ui.about;

import android.arch.lifecycle.ViewModelProvider;

import com.boilerplate.mvvm_sample.ui.ViewModelProviderFactory;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Louis on 2018/4/10.
 */
@Module
public class AboutFragmentModule {

    @Provides
    ViewModelProvider.Factory provideAboutFragmentViewModel(AboutFragmentViewModel aboutFragmentViewModel) {
        return new ViewModelProviderFactory<>(aboutFragmentViewModel);
    }

    @Provides
    AboutFragmentViewModel aboutFragmentViewModel() {
        return new AboutFragmentViewModel();
    }

}
