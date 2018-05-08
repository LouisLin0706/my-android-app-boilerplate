package com.boilerplate.base.kotlin

/**
 * Created by Louis on 2018/4/27.
 */

interface IPresenter<S : IView> {

    fun attachView(view: S)


    fun detachView()
}
