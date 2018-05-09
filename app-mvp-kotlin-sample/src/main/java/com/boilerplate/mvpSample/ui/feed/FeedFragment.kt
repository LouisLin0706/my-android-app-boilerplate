package com.boilerplate.mvpSample.ui.feed

import com.boilerplate.base.kotlin.KBaseLazyFragment

/**
 * Created by Louis on 2018/5/9.
 */
class FeedFragment : KBaseLazyFragment<FeedPresenter, FeedContract.View>(), FeedContract.View {
    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun completeLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMessage(msg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override var mPresenter: FeedPresenter
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override var mIView: FeedContract.View
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override var layoutId: Int
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    override fun setupActivityComponent() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initEventAndData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onUserInvisible() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFirstUserVisible() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onUserVisible() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFirstUserInvisible() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}