package com.sample.app_player_sample.arch.base

import io.reactivex.Observable

/**
 * Created by Louis on 2018/10/29.
 */
abstract class Presenter<SSE, UIE>(uiView: UiView<UIE>, screenState: Observable<SSE>, destoryObservable: Observable<Unit>) {
}