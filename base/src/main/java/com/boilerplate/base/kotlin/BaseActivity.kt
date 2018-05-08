package com.boilerplate.base.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by Louis on 2018/4/27.
 */
abstract class BaseActivity<T, S> : AppCompatActivity() where T : IPresenter<S>, S : IView {

    protected abstract var mPresenter: T
    protected abstract var mView: S
    protected abstract val layout: Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)
        setupActivityComponent()
        mPresenter.attachView(mView)
        initEventAndData()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    protected abstract fun getLayout()

    protected abstract fun setupActivityComponent()

    protected abstract fun initEventAndData()
}