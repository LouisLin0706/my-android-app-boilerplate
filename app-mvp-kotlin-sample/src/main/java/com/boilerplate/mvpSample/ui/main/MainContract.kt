package com.boilerplate.mvpSample.ui.main

import com.boilerplate.base.kotlin.IPresenter
import com.superc.base.ILoadingView

/**
 * Created by Louis on 2018/5/1.
 */
class MainContract {

    interface View : ILoadingView {

    }

    interface Presenter : IPresenter<MainContract.View> {

    }
}