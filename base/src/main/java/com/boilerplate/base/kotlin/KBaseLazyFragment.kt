package com.boilerplate.base.kotlin

import android.os.Bundle
import android.support.v4.app.Fragment
import java.lang.reflect.Field

/**
 * Created by Louis on 2018/4/30.
 */
abstract class KBaseLazyFragment<T, S> : KBaseFragment<T, S>() where T : KIPresenter<S>, S : KIView {

    private val isFirstResume: Boolean = true
    private var isFirstVisible: Boolean = true
    private var isFirstInvisible: Boolean = true
    private var isPrepared: Boolean = true

    override fun onDetach() {
        super.onDetach()
        try {
            val childFramgnetManager: Field = Fragment::class.java.getDeclaredField("mChildFragmentManager")
            childFramgnetManager.setAccessible(true)
            childFramgnetManager.set(this, null)

        } catch (e: NoSuchFieldException) {
            throw RuntimeException(e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initPrepare()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false
                initPrepare()
            } else {
                onUserVisible()
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false
                onFirstUserInvisible()
            } else {
                onUserInvisible()
            }
        }
    }

    @Synchronized private fun initPrepare() {
        if (isPrepared) {
            onFirstUserVisible()
        } else {
            isPrepared = true
        }
    }

    protected abstract fun onUserInvisible()
    protected abstract fun onFirstUserVisible()
    protected abstract fun onUserVisible()
    protected abstract fun onFirstUserInvisible()
}