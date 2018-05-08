package com.boilerplate.mvpSample.ui.main

import com.boilerplate.base.kotlin.BaseActivity
import com.boilerplate.mvp_sample.R

/**
 * Created by Louis on 2018/5/1.
 */
class MainActivity : BaseActivity<MainPresenter, MainContract.View>(), MainContract.View {

    override var mView: MainContract.View
        get() = this
        set(value) {}
    override var mPresenter: MainPresenter
        get() = MainPresenter()
        set(value) {}
    override val layout: Int
        get() = R.layout.activity_main


    override fun showLoading() {
    }

    override fun completeLoading() {

    }

    override fun showMessage(msg: String) {

    }

    override fun getLayout() {

    }

    override fun setupActivityComponent() {

    }

    override fun initEventAndData() {

    }


}