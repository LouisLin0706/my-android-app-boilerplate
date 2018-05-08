package com.boilerplate.lib_player.extension;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Louis on 2018/4/20.
 */

public class AutoSyncControllerComponent {

    private Disposable disposableLoop;

    public AutoSyncControllerComponent() {

    }


    private void startLoop() {
        stopLoop();
        disposableLoop = Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        //do someting stuff
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    private void stopLoop() {
        if (disposableLoop != null && disposableLoop.isDisposed() == false) {
            disposableLoop.dispose();
        }
    }
}
