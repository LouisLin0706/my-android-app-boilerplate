package com.sample.app_player_sample.arch.base

import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by Louis on 2018/10/29.
 */
abstract class UiView<ULE>(container: ViewGroup) {

    protected val userInteractionEvents: PublishSubject<ULE> = PublishSubject.create()


    fun getUserInteractionEvents() : Observable<ULE> {
        return userInteractionEvents
    }


}