package com.boilerplate.lib_player.component;

import com.boilerplate.lib_player.core.HybridRulePlayBack;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Louis on 2018/4/20.
 * If your view implement IControllerView this interface
 * Then use this component will auto sync seekbar,time refresh every sec
 * Also If you want know position or duration every sec , You can set up Listener
 */
public class AutoSyncControllerComponent {

    private Disposable disposableLoop;
    private List<OnAutoControllerListener> autoControllerListeners = new ArrayList<>();

    public AutoSyncControllerComponent() {
    }


    public void addOnAutoControllerListener(OnAutoControllerListener onAutoControllerListener) {
        autoControllerListeners.add(onAutoControllerListener);
    }

    public void removeAutoControllerListener(OnAutoControllerListener onAutoControllerListener) {
        autoControllerListeners.remove(onAutoControllerListener);
    }

    public void startLoop(final HybridRulePlayBack hybridPlayBack) {
        stopLoop();
        disposableLoop = Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        long position = hybridPlayBack.getCurrentPosition();
                        long duration = hybridPlayBack.getDuration();
                        if (duration == 0) {
                            if (hybridPlayBack.getHybridPlayerView().getCTextView() != null) {
                                hybridPlayBack.getHybridPlayerView().getCTextView().setText("00:00");
                            }
                            if (hybridPlayBack.getHybridPlayerView().getETextView() != null) {
                                hybridPlayBack.getHybridPlayerView().getETextView().setText("00:00");
                            }
                        } else {
                            for (OnAutoControllerListener onAutoControllerListener : autoControllerListeners) {
                                onAutoControllerListener.onProgressEffectRunning(position, duration);
                            }
                            long seekBarPosition = position * 1000L / duration;
                            if (hybridPlayBack.getHybridPlayerView().getSeekBar() != null) {
                                hybridPlayBack.getHybridPlayerView().getSeekBar().setProgress((int) seekBarPosition);
                            }

                            if (hybridPlayBack.getHybridPlayerView().getETextView() != null) {
                                hybridPlayBack.getHybridPlayerView().getETextView().setText(foramtTime2String(duration));
                            }
                            if (hybridPlayBack.getHybridPlayerView().getCTextView() != null) {
                                hybridPlayBack.getHybridPlayerView().getCTextView().setText(foramtTime2String(position));
                            }
                            int percent = hybridPlayBack.getBufferedPercentage();
                            int progress = 0;
                            if (hybridPlayBack.getHybridPlayerView().getSeekBar() != null) {
                                progress = hybridPlayBack.getHybridPlayerView().getSeekBar().getProgress();
                            }
                            int bufferPercent = progress + percent;
                            if (hybridPlayBack.getHybridPlayerView().getSeekBar() != null) {
                                hybridPlayBack.getHybridPlayerView().getSeekBar().setSecondaryProgress(bufferPercent);
                            }

                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    private String foramtTime2String(long timeMs) {
        StringBuilder mFormatBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());

        long totalSeconds = timeMs / 1000;

        long seconds = totalSeconds % 60;
        long minutes = (totalSeconds / 60) % 60;
        long hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    //TODO maybe include lifecycle handle
    public void stopLoop() {
        if (disposableLoop != null && disposableLoop.isDisposed() == false) {
            disposableLoop.dispose();
        }
    }
}
