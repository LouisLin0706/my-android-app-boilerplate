package com.boilerplate.mvvm_sample.ui.feed;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;

import com.boilerplate.base.mvvm.BaseFragment;
import com.boilerplate.mvvm_sample.BR;
import com.boilerplate.mvvm_sample.R;
import com.boilerplate.mvvm_sample.databinding.FragmentFeedBinding;

import javax.inject.Inject;

/**
 * Created by Louis on 2018/4/9.
 */

public class FeedFragment extends BaseFragment<FragmentFeedBinding, FeedViewModel> implements FeedNavigator {
    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    private FeedViewModel feedViewModel;


    @Override
    public FeedViewModel getViewModel() {
        feedViewModel = ViewModelProviders.of(this, mViewModelFactory).get(FeedViewModel.class);
        return feedViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_feed;
    }
}
