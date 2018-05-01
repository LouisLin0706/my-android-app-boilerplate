package com.boilerplate.base.kotlin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.boilerplate.base.mvp.IPresenter
import com.boilerplate.base.mvp.IView

/**
 * Created by Louis on 2018/4/30.
 */
abstract class BaseFragment<V : IView, T : IPresenter<V>> : Fragment(), IView {
    protected abstract var mPresenter: T
    protected lateinit var mView: View
    protected abstract var layoutId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(layoutId, null)
        setupActivityComponent()
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.attachView(this as V)


    }

    protected abstract fun setupActivityComponent()

    protected abstract fun initEventAndData()

}