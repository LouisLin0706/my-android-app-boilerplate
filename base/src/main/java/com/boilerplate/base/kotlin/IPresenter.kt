package com.superc.base

/**
 * Created by Louis on 2018/4/27.
 */
interface IPresenter<in T : IView> {
    fun attachView(view: T)
    fun detachView()
}