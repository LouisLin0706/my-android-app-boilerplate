package com.superc.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Louis on 2018/4/27.
 */
class BasePresenter<T : IView> : IPresenter<T> {

    override fun attachView(view: T) {
        this.view = view
    }


    protected var mCompositeDisposable: CompositeDisposable? = null


    override fun detachView() {
    }

    protected fun unSubsrcibe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable!!.clear()
            mCompositeDisposable!!.dispose()
        }
    }

    protected fun addSubscribe(disposable: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable!!.add(disposable)
    }

    protected var view: T? = null
}