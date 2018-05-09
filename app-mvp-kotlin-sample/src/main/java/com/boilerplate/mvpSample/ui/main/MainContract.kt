package com.boilerplate.mvpSample.ui.main

import com.boilerplate.base.kotlin.KIPresenter
import com.superc.base.KILoadingView

/**
 * Created by Louis on 2018/5/1.
 */
class MainContract {

    interface View : KILoadingView {

    }

    interface Presenter : KIPresenter<MainContract.View> {

    }
}