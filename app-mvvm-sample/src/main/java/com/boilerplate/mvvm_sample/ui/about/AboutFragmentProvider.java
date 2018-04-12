package com.boilerplate.mvvm_sample.ui.about;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Louis on 2018/4/9.
 */
@Module
public abstract class AboutFragmentProvider {

    @ContributesAndroidInjector(modules = AboutFragmentModule.class)
    abstract AboutFragment provideAboutFragmentFactory();
}
