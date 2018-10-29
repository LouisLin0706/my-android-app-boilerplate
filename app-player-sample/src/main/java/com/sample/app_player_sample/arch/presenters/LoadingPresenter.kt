package com.sample.app_player_sample.arch.presenters

import com.sample.app_player_sample.arch.base.Presenter
import com.sample.app_player_sample.arch.base.UiView
import com.sample.app_player_sample.arch.event.ScreenStateEvent
import com.sample.app_player_sample.arch.event.UserInteractionEvent
import io.reactivex.Observable

/**
 * Created by Louis on 2018/10/29.
 */
class LoadingPresenter<SSE : ScreenStateEvent, UIE : UserInteractionEvent>(uiView: UiView<UIE>,
                                                                           screenStateEvent: Observable<SSE>,
                                                                           destoryObservable: Observable<Unit>) : Presenter<SSE, UIE>(
        uiView, screenStateEvent, destoryObservable
) {



    init {
        screenStateEvent
                .takeUntil(destoryObservable)
                .subscribe {
                    when (it) {
                        ScreenStateEvent.Error -> {

                        }
                        ScreenStateEvent.Loaded -> {

                        }
                        ScreenStateEvent.Loading -> {

                        }
                    }

                }
    }

}