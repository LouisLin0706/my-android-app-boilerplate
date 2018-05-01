package com.superc.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

/**
 * Created by Louis on 2018/4/27.
 */
abstract class BaseActivity<in V : IView, T : IPresenter<V>> : AppCompatActivity(), IView {

    protected abstract var mPresenter: T
    protected lateinit var fragmentBackStack: List<Fragment>
    protected abstract val layout: Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)
        fragmentBackStack = ArrayList()
        setupActivityComponent()
        mPresenter.attachView(this as V)
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