package com.boilerplate.mvvm_sample.ui.about;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.boilerplate.base.mvvm.BaseFragment;
import com.boilerplate.mvvm_sample.BR;
import com.boilerplate.mvvm_sample.R;
import com.boilerplate.mvvm_sample.databinding.FragmentAboutBinding;

import javax.inject.Inject;

/**
 * Created by Louis on 2018/4/10.
 */

public class AboutFragment extends BaseFragment<FragmentAboutBinding, AboutFragmentViewModel> implements AboutNavigator {
    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    private AboutFragmentViewModel aboutFragmentViewModel;

    FragmentAboutBinding fragmentAboutBinding;

    public static AboutFragment newInstance() {
        
        Bundle args = new Bundle();
        
        AboutFragment fragment = new AboutFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public AboutFragmentViewModel getViewModel() {
        aboutFragmentViewModel = ViewModelProviders.of(this, mViewModelFactory).get(AboutFragmentViewModel.class);
        return aboutFragmentViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aboutFragmentViewModel.setNavigator(this);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentAboutBinding = getViewDataBinding();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_about;
    }

}
