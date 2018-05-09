package com.boilerplate.base.kotlin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Louis on 2018/4/30.
 */
abstract class KBaseFragment<T, S> : Fragment() where T : KIPresenter<S>, S : KIView {
    protected abstract var mPresenter: T
    protected lateinit var mView: View
    protected abstract var mIView: S
    protected abstract var layoutId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(layoutId, null)
        setupActivityComponent()
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.attachView(mIView)


    }

    protected abstract fun setupActivityComponent()

    protected abstract fun initEventAndData()

}