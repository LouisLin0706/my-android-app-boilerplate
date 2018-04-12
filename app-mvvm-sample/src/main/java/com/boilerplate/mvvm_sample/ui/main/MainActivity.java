/*
 * Copyright (C) 2018 Tse Ju Lin.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.boilerplate.mvvm_sample.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.boilerplate.base.mvvm.BaseActivity;
import com.boilerplate.mvvm_sample.BR;
import com.boilerplate.mvvm_sample.R;
import com.boilerplate.mvvm_sample.databinding.ActivityMainBinding;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by Louis on 2018/4/9.
 */

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainNavigator, HasSupportFragmentInjector, BottomNavigationView.OnNavigationItemSelectedListener {

//    @Inject
//    MainPagerAdapter mainPagerAdapter;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    MainViewModel mainViewModel;

    private ActivityMainBinding mActivityMainBinding;

    @Override
    public MainViewModel getViewModel() {
        return mainViewModel;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = getViewDataBinding();
//        mainPagerAdapter.setCount(2);
//        mActivityMainBinding.feedViewPager.setAdapter(mainPagerAdapter);
//        mActivityMainBinding.feedViewPager.setOffscreenPageLimit(mainPagerAdapter.getCount());
        mainViewModel.setNavigator(this);
        mActivityMainBinding.bottomNavigation.setOnNavigationItemSelectedListener(this);
//        onNavigationItemSelected(mActivityMainBinding.bottomNavigation.getMenu().getItem(0));
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
