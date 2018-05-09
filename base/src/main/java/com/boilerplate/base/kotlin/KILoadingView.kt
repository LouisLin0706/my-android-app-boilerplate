package com.superc.base

import com.boilerplate.base.kotlin.KIView

/**
 * Created by Louis on 2018/4/27.
 */
interface KILoadingView : KIView {

    fun showLoading()

    fun completeLoading()

    fun showMessage(msg: String)
}