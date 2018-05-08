package com.superc.base

import com.boilerplate.base.kotlin.IView

/**
 * Created by Louis on 2018/4/27.
 */
interface ILoadingView : IView {

    fun showLoading()

    fun completeLoading()

    fun showMessage(msg: String)
}