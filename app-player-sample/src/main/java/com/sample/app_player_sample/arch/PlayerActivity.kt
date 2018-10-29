package com.sample.app_player_sample.arch

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import com.sample.app_player_sample.arch.event.ScreenStateEvent
import com.sample.app_player_sample.arch.event.UserInteractionEvent
import com.sample.app_player_sample.arch.presenters.ErrorPresenter
import com.sample.app_player_sample.arch.presenters.LoadingPresenter
import com.sample.app_player_sample.arch.views.ErrorView
import com.sample.app_player_sample.arch.views.LoadIngView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by Louis on 2018/10/29.
 */
class PlayerActivity : AppCompatActivity() {

    private var destroyObservable: PublishSubject<Unit> = PublishSubject.create()
    private var screenStateEvent: PublishSubject<ScreenStateEvent> = PublishSubject.create()
    private lateinit var loadIngView: LoadIngView
    private lateinit var errorView: ErrorView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun initComponents(viewGroup: ViewGroup) {
        loadIngView = LoadIngView(viewGroup)
        LoadingPresenter(loadIngView, screenStateEvent, destroyObservable)
        errorView = ErrorView(viewGroup)
        ErrorPresenter(errorView, screenStateEvent, destroyObservable)
    }

    private fun initUserInteractionEventsObservable() {

        Observable.merge<UserInteractionEvent>(
                loadIngView.getUserInteractionEvents(),
                errorView.getUserInteractionEvents()
        ).takeUntil(destroyObservable)
                .subscribe({
                    when (it) {
                        UserInteractionEvent.IntentTapRetry -> {

                        }
                    }
                })
    }


    fun testSimulation() {
        screenStateEvent.onNext(ScreenStateEvent.Loading)
        screenStateEvent.onNext(ScreenStateEvent.Loaded)
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyObservable.onNext(Unit)
        destroyObservable.onComplete()
    }
}