package com.boilerplate.base.mvp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity<T extends IPresenter> extends AppCompatActivity implements IView {

    protected T presenter;
    protected ProgressDialog progressDialog;
    private int resId;
    protected List<Fragment> fragmentBackStack;
    private boolean isAnimationRunning = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        fragmentBackStack = new ArrayList<>();
        presenter = getPresenter();
        setupActivityComponent();
        if (presenter != null) presenter.attachView(this);
        initEventAndData();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    protected abstract T getPresenter();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) presenter.detachView();
    }

    protected abstract int getLayout();

    protected abstract void initEventAndData();

    protected abstract void setupActivityComponent();
}
