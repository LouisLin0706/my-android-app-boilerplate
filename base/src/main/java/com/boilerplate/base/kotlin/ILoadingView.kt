package com.superc.base

/**
 * Created by Louis on 2018/4/27.
 */
interface ILoadingView : IView {

    fun showLoading()

    fun completeLoading()

    fun showMessage(msg: String)
}