package com.boilerplate.mvvm_sample.ui.main;

import com.boilerplate.base.mvvm.BaseActivity;
import com.boilerplate.mvvm_sample.BR;
import com.boilerplate.mvvm_sample.R;
import com.boilerplate.mvvm_sample.databinding.ActivityMainBinding;

import javax.inject.Inject;

/**
 * Created by Louis on 2018/4/9.
 */

public class MainActivity extends BaseActivity<ActivityMainBinding, MainActivityViewModel> implements MainNavigator {

    @Inject
    MainActivityViewModel mainActivityViewModel;

    @Override
    public MainActivityViewModel getViewModel() {
        return mainActivityViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
}
