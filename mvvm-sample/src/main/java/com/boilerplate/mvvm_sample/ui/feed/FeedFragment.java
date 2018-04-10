package com.boilerplate.mvvm_sample.ui.feed;

import com.boilerplate.base.mvvm.BaseFragment;
import com.boilerplate.mvvm_sample.BR;
import com.boilerplate.mvvm_sample.R;
import com.boilerplate.mvvm_sample.databinding.FragmentFeedBinding;

import javax.inject.Inject;

/**
 * Created by Louis on 2018/4/9.
 */

public class FeedFragment extends BaseFragment<FragmentFeedBinding, FeedFragmentViewModel> implements FeedNavigator {


    @Inject
    FeedFragmentViewModel feedFragmentViewModel;

    @Override
    public FeedFragmentViewModel getViewModel() {
        return feedFragmentViewModel;
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
