package com.boilerplate.mvpSample.ui.feed

import com.boilerplate.base.kotlin.KIPresenter
import com.superc.base.KILoadingView


/**
 * Created by Louis on 2018/5/9.
 */
class FeedContract {


    interface View : KILoadingView {

    }

    interface Presenter : KIPresenter<View> {

    }
}