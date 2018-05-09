package com.boilerplate.base.kotlin

/**
 * Created by Louis on 2018/4/27.
 */

interface KIPresenter<S : KIView> {

    fun attachView(view: S)


    fun detachView()
}
