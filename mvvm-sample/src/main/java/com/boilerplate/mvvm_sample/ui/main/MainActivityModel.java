package com.boilerplate.mvvm_sample.ui.main;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Louis on 2018/4/9.
 */
@Module
public class MainActivityModel {

    @Provides
    MainViewModel provideMainViewModel() {
        return new MainViewModel();
    }

}
