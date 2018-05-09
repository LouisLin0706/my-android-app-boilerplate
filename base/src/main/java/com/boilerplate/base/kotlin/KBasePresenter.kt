package com.boilerplate.base.kotlin

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Louis on 2018/4/27.
 */
open class KBasePresenter<T> : KIPresenter<T> where T : KIView {


    protected var view: T? = null
    protected var mCompositeDisposable: CompositeDisposable? = null

    override fun attachView(view: T) {
        this.view = view
    }

    protected fun unSubscribe() {
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

    override fun detachView() {
        this.view = null
        unSubscribe()
    }
}
