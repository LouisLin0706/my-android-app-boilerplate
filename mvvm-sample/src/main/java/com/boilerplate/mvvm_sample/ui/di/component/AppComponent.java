package com.boilerplate.mvvm_sample.ui.di.component;

import android.app.Application;

import com.boilerplate.mvvm_sample.ui.MvvmApp;
import com.boilerplate.mvvm_sample.ui.di.builder.ActivityBuilder;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by Louis on 2018/4/10.
 */
@Singleton
@Component(modules = {AndroidInjectionModule.class, ActivityBuilder.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(MvvmApp mvvmApp);
}
