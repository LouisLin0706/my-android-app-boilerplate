package com.boilerplate.mvvm_sample.ui.di.module;

import android.app.Application;
import android.content.Context;

import com.boilerplate.mvvm_sample.ui.di.scopes.PerApplication;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Louis on 2018/4/10.
 */
@Module
public class AppModule {

    @Provides
    @PerApplication
    Context provideContext(Application application) {
        return application;
    }

}
