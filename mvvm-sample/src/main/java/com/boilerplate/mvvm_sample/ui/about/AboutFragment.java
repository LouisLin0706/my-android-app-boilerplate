package com.boilerplate.mvvm_sample.ui.about;

import com.boilerplate.base.mvvm.BaseFragment;
import com.boilerplate.mvvm_sample.databinding.FragmentAboutBinding;

/**
 * Created by Louis on 2018/4/10.
 */

public class AboutFragment extends BaseFragment<FragmentAboutBinding, AboutFragmentViewModel> implements AboutNavigator {

    @Override
    public AboutFragmentViewModel getViewModel() {
        return null;
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return 0;
    }
}
