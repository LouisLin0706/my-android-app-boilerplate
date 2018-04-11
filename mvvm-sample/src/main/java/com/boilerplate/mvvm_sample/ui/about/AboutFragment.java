package com.boilerplate.mvvm_sample.ui.about;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;

import com.boilerplate.base.mvvm.BaseFragment;
import com.boilerplate.mvvm_sample.BR;
import com.boilerplate.mvvm_sample.R;
import com.boilerplate.mvvm_sample.databinding.FragmentAboutBinding;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by Louis on 2018/4/10.
 */

public class AboutFragment extends BaseFragment<FragmentAboutBinding, AboutFragmentViewModel> implements AboutNavigator, HasSupportFragmentInjector {
    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    private AboutFragmentViewModel aboutFragmentViewModel;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Override
    public AboutFragmentViewModel getViewModel() {
        aboutFragmentViewModel = ViewModelProviders.of(this, mViewModelFactory).get(AboutFragmentViewModel.class);
        return aboutFragmentViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_about;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
